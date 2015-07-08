package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.apache.http.protocol.HttpService;
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
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class ChangeTimePostedCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static NewPostService service = factory.create(NewPostService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("post_id"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String dateDelete = request.getParameter("date_delete");
        String timeDelete = request.getParameter("time_delete");
        NewPost post = service.getById(postId);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            post.setPostTime(formatter.parse(date + " " + time));
            if (request.getParameter("checked").equals("true")) {
                post.setDeleteTime(formatter.parse(dateDelete + " " + timeDelete));
            }
        } catch (ParseException ignored) {
        }
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (service.update(postId, post)) {
            json.put("status", "success");
            json.put("msg", "Змінено успішно");
        } else {
            json.put("status", "error");
            json.put("msg", "Виникла помилка");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
    }
}
