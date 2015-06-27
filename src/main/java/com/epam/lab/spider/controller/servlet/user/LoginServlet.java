package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.auth.*;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class LoginActionFactory extends ActionFactory {

        public LoginActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAuthCommand());
            commands.put("signIn", new SignInCommand());
            commands.put("signOut", new SignOutCommand());
            commands.put("vkAuth", new VkAuthCommand());
            commands.put("vkAuthResponse", new VkAuthResponseCommand());
            commands.put("fbAuth", new FbAuthCommand());
            commands.put("fbAuthResponse", new FbAuthResponseCommand());

        }

        @Override
        public void action(HttpServletRequest request, HttpServletResponse response) throws
                ServletException, IOException {
            if (request.getParameter("code") != null) {
                if (request.getParameter("code").length() < 80)
                    commands.get("vkAuthResponse").execute(request, response);
                else
                    commands.get("fbAuthResponse").execute(request, response);
                return;
            }
            super.action(request, response);
        }

    }

}
