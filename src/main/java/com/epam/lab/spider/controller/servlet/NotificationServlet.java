package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.notf.GetNotificationCommand;
import com.epam.lab.spider.controller.command.notf.RemoveNotificationCommand;
import com.epam.lab.spider.controller.command.notf.ShowNotificationCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Boyarsky Vitaliy
 */
public class NotificationServlet extends HttpServlet {

    private static ActionFactory factory = new NotificationActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class NotificationActionFactory extends ActionFactory {
        public NotificationActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowNotificationCommand());
            commands.put("remove", new RemoveNotificationCommand());
            commands.put("get", new GetNotificationCommand());
        }
    }
}
