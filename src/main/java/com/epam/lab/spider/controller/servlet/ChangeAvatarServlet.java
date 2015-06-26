package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.profile.ChangeAvatarCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 22.06.2015.
 */
public class ChangeAvatarServlet extends HttpServlet {
    private static ActionFactory factory = new ChangeAvatarFactory();

    private static class ChangeAvatarFactory extends ActionFactory {
        public ChangeAvatarFactory() {
            commands = new HashMap<>();
            commands.put("changeavatar", new ChangeAvatarCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);

    }
}
