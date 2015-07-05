package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
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
 * Created by Boyarsky Vitaliy on 05.07.2015.
 */
public class PublishPostByIdCommand implements ActionCommand {


    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);
    private static NewPostService newPostService = factory.create(NewPostService.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        post.getAttachments();
        post.setUserId(user.getId());
        postService.insert(post);
        // Створюємо new post
        NewPost newPost = new NewPost();
        newPost.setUserId(user.getId());
        newPost.setPostId(post.getId());
        newPost.setState(NewPost.State.CREATED);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            newPost.setPostTime(formatter.parse(date + " " + time));
            if (request.getParameter("checked").equals("true")) {
                newPost.setDeleteTime(formatter.parse(dateDelete + " " + timeDelete));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray array = new JSONArray();
        JSONObject obj;
        newPost.setDeleted(false);
        String[] groupsId = groups.split(",");
        // Створюємо пост для кожної стіни
        for (String id : groupsId) {
            newPost.setWallId(Integer.parseInt(id));
            if (newPostService.insert(newPost)) {
                obj = new JSONObject();
                obj.put("status", "success");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.success")));
            } else {
                obj = new JSONObject();
                obj.put("status", "error");
                obj.put("msg", UTF8.encoding(bundle.getString("notification.delete.binding.error")));
            }
            array.put(obj);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(array.toString());
    }

}
