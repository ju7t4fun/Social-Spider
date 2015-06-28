package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 28.06.2015.
 */
public class RemovePostCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        JSONObject json = new JSONObject();
        if (service.delete(id)) {
            json.put("status", "success");
            json.put("msg", "Delete post");
        } else {
            json.put("status", "error");
            json.put("msg", "Error");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

}
