package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.signup.ActivateCommand;
import com.epam.lab.spider.controller.command.user.signup.RegisterCommand;
import com.epam.lab.spider.controller.command.user.signup.ShowActivationCommand;
import com.epam.lab.spider.controller.command.user.signup.ShowRegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Dzyuba Orest
 */
public class RegisterServlet extends HttpServlet {

    private static ActionFactory factory = new RegisterActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class RegisterActionFactory extends ActionFactory {

        public RegisterActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowRegisterCommand());
            commands.put("register", new RegisterCommand());
            commands.put("activateResult", new ShowActivationCommand());
            commands.put("activate", new ActivateCommand());
        }

    }

}
