package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class PublishPostByIdCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(PublishPostByIdCommand.class);

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);
    private static PostingTaskService postingTaskService = factory.create(PostingTaskService.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("---------------------START------------------------------------------");
        // Вхідні параметри
        Integer postId = Integer.parseInt(request.getParameter("postId"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String dateDelete = request.getParameter("date_delete");
        String timeDelete = request.getParameter("time_delete");
        String groups = request.getParameter("groups");

        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        User user = (User) session.getAttribute("user");
        Post post = postService.getById(postId);
        LOG.debug("---- " + post.getId() + " --- " + post.getUserId());
        if (post.getUserId() < 0) {
            post.getAttachments();
            post.setUserId(user.getId());
            postService.insert(post);
        }
        // Створюємо new post
        LOG.debug("---- " + post.getId() + " --- " + post.getUserId());
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

        JSONArray array = new JSONArray();
        JSONObject obj;
        postingTask.setDeleted(false);
        String[] groupsId = groups.split(",");

        // Створюємо пост для кожної стіни
        for (String id : groupsId) {
            postingTask.setWallId(Integer.parseInt(id));
            if (postingTaskService.insert(postingTask)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.post.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.post.error")));
            }
            array.put(obj);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
        LOG.debug("---------------------END------------------------------------------");
    }

}
