package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.AddPostCommand;
import com.epam.lab.spider.controller.command.ShowNewPostCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 25.06.2015.
 */
public class AddPostServlet extends HttpServlet {
    private static ActionFactory factory = new AddPostFactory();

    private static class AddPostFactory extends ActionFactory {
        public AddPostFactory() {
            commands = new HashMap<>();
            commands.put("addpost", new AddPostCommand());
            commands.put("default", new ShowNewPostCommand());
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String currentDate = date.format(now);
        String currentTime = time.format(now);
        request.getSession().setAttribute("date", currentDate);
        request.getSession().setAttribute("time", currentTime);
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String currentDate = date.format(now);
        String currentTime = time.format(now);
        request.getSession().setAttribute("date", currentDate);
        request.getSession().setAttribute("time", currentTime);
        factory.action(request, response);
    }
}
