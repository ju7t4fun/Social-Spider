package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Marian Voronovskyi
 */
public class SavePostFromFeedCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService postService = factory.create(PostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        JSONObject json = new JSONObject();
        User user = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = postService.getById(id);
        post.getAttachments();
        post.setUserId(user.getId());
        if (postService.insert(post)) {
            json.put("status", "success");
            json.put("message", UTF8.encoding(bundle.getString("notification.create.post.success")));
        }
        response.setContentType("application/json");
        response.setContentType("UTF-8");
        response.getWriter().write(json.toString());
    }
}
