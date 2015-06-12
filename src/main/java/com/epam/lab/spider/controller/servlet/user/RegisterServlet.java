package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.auth.RegisterCommand;
import com.epam.lab.spider.controller.command.user.auth.ShowRegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Dmytro on 11.06.2015.
 */
public class RegisterServlet extends HttpServlet {
    private static ActionFactory factory = new RegisterActionFactory();

    private static class RegisterActionFactory extends ActionFactory {

        public RegisterActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowRegisterCommand());
            commands.put("register", new RegisterCommand());
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
