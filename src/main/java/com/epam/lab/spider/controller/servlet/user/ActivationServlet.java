package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.auth.ActivateCommand;
import com.epam.lab.spider.controller.command.user.auth.RegisterCommand;
import com.epam.lab.spider.controller.command.user.auth.ShowActivationCommand;
import com.epam.lab.spider.controller.command.user.auth.ShowRegisterCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class ActivationServlet extends HttpServlet {
    private static ActionFactory factory = new ActivationActionFactory();

    private static class ActivationActionFactory extends ActionFactory {

        public ActivationActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowActivationCommand());
            commands.put("activate", new ActivateCommand());
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
