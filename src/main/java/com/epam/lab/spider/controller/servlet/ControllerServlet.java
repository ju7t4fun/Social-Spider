package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.controller.LocaleCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class ControllerServlet extends HttpServlet {

    private static ActionFactory factory = new ApiActionFactory();

    private static class ApiActionFactory extends ActionFactory {

        public ApiActionFactory() {
            commands = new HashMap<String, ActionCommand>();
            commands.put("locale", new LocaleCommand());
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        ActionCommand command = factory.action(request, response);
        command.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        ActionCommand command = factory.action(request, response);
        command.doGet(request, response);
    }

}
