package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
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
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 25.06.2015.
 */
public class AddPostCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);
    private static NewPostService newPostService = factory.create(NewPostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setContentType("UTF-8");
        HttpSession session = request.getSession();

        Map<String, String> urlType = (Map<String, String>) request.getSession().getAttribute("files_url");
        String message = request.getParameter("message");
        String title = request.getParameter("title");
        if (title.length() != 0) {
            message = title + "\n" + message;
        }
        String tagArr = request.getParameter("tags");
        if (tagArr.length() != 0) {
            String[] tags = tagArr.split(",");
            message = message + "\n";
            for (String tag : tags) {
                message = message + "#" + tag + " ";
            }
        }

        Post post = new Post();
        Attachment attachment;
        Set<Attachment> attachments = new HashSet<>();
        for (Map.Entry<String, String> entry : urlType.entrySet()) {
            attachment = new Attachment();
            attachment.setPayload(entry.getKey());
            attachment.setType(Attachment.Type.valueOf(entry.getValue()));
            attachment.setDeleted(false);
            attachments.add(attachment);
        }
        post.setMessage(message.trim());
        post.setDeleted(false);
        post.setAttachments(attachments);

        if (request.getParameter("typePost") == null) {
            postService.insert(post);
            session.setAttribute("toastr_notification", "success|" + "Створено новий пост!");
            response.sendRedirect("/post");
            return;
        } else {
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String dateDelete = request.getParameter("date_delete");
            String timeDelete = request.getParameter("time_delete");
            String groups = request.getParameter("groups");

            NewPost newPost = new NewPost();
            newPost.setPost(post);
            newPost.setState(NewPost.State.CREATED);

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                newPost.setPostTime(formatter.parse(date + " " + time));
                if (dateDelete.length() > 0) {
                    newPost.setDeleteTime(formatter.parse(dateDelete + " " + timeDelete));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            newPost.setDeleted(false);
            newPost.setWallId(1);
            newPostService.insert(newPost);
        }

        session.setAttribute("toastr_notification", "success|" + "Створено новий пост!");
        json.put("status", "success");
        response.getWriter().write(json.toString());
    }
}
