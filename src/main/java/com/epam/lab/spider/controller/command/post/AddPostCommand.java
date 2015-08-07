package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.ServerResolver;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marian Voronovskyi
 */
public class AddPostCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(AddPostCommand.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);
    private static PostingTaskService postingTaskService = factory.create(PostingTaskService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = null;
        {
            Object userObject = session.getAttribute("user");
            if (userObject != null && userObject instanceof User) user = (User) userObject;
            if (user == null) {
                response.sendError(401);
                return;
            }
        }
        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setContentType("UTF-8");


        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");
        String message = request.getParameter("message");
        if (message == null || Objects.equals(message, "")) {
            message = "Empty post";
        }
        message = ReplaceHtmlTags.reaplaceAll(message);
        String title = request.getParameter("title");
        title = ReplaceHtmlTags.reaplaceAll(title);
        if (title.length() != 0) {
            message = title + "\n" + message;
        }
        String tagArr = request.getParameter("tags");
        tagArr = ReplaceHtmlTags.reaplaceAll(tagArr);
        if (tagArr.length() != 0) {
            String[] tags = tagArr.split(",");
            message = message + "\n";
            for (String tag : tags) {
                message = message + "#" + tag + " ";
            }
        }

        Post post = BasicEntityFactory.getSynchronized().createPost();
        post.setUserId(user.getId());
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        for (Map.Entry<String, String> entry : urlType.entrySet()) {
            attachment = BasicEntityFactory.getSynchronized().createAttachment();
            attachment.setPayload(ServerResolver.getServerPath(request) + entry.getKey());
            attachment.setType(Attachment.Type.valueOf(entry.getValue()));
            attachment.setDeleted(false);
            attachments.add(attachment);
        }
        post.setMessage(message.trim());
        post.setDeleted(false);
        post.setAttachments(attachments);

        postService.insert(post);
        if (request.getParameter("typePost") == null) {
            session.setAttribute("toastr_notification", "success|" + "Створено новий пост!");
            response.sendRedirect(ServerResolver.getServerPath(request)+"/post?action=created");
            return;
        } else {
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String dateDelete = request.getParameter("date_delete");
            String timeDelete = request.getParameter("time_delete");
            String groups = request.getParameter("groups");

            PostingTask postingTask = BasicEntityFactory.getSynchronized().createPostingTask();

            postingTask.setUserId(user.getId());
            postingTask.setPostId(post.getId());
            postingTask.setState(PostingTaskImpl.State.CREATED);

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                postingTask.setPostTime(formatter.parse(date + " " + time));
                if (request.getParameter("checked").equals("true")) {
                    postingTask.setDeleteTime(formatter.parse(dateDelete + " " + timeDelete));
                }
            } catch (ParseException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }

            postingTask.setDeleted(false);
            String[] groupsId = groups.split(",");
            for (String id : groupsId) {
                postingTask.setWallId(Integer.parseInt(id));
                postingTaskService.insert(postingTask);
            }
        }

        session.setAttribute("toastr_notification", "success|" + "Створено новий пост!");
        json.put("status", "success");
        response.getWriter().write(json.toString());
    }
}
