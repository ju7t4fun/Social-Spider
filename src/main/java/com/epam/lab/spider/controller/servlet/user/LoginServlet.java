package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.auth.ShowAuthCommand;
import com.epam.lab.spider.controller.command.user.auth.VkAuthCommand;
import com.epam.lab.spider.controller.command.user.auth.VkAuthResponseCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class LoginServlet extends HttpServlet {

    private static ActionFactory factory = new LoginActionFactory();

    private static class LoginActionFactory extends ActionFactory {

        public LoginActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAuthCommand());
            commands.put("vkAuth", new VkAuthCommand());
            commands.put("vkAuthResponse", new VkAuthResponseCommand());
        }

        @Override
        public void action(HttpServletRequest request, HttpServletResponse response) throws
                ServletException, IOException {
            if (request.getParameter("code") != null) {
                commands.get("vkAuthResponse").execute(request, response);
                return;
            }
            super.action(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }
}
