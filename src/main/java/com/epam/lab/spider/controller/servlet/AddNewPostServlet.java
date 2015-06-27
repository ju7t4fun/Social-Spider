package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.AddNewPostCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 26.06.2015.
 */
public class AddNewPostServlet extends HttpServlet {

    private static ActionFactory factory = new AddNewPostFactory();

    private static class AddNewPostFactory extends ActionFactory {
        public AddNewPostFactory() {
            commands = new HashMap<>();
            commands.put("addnewpost", new AddNewPostCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
