package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.support.ShowAdminSupportCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 23.06.2015.
 */
public class SupportServlet extends HttpServlet {

    private static ActionFactory factory = new InBoxActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class InBoxActionFactory extends ActionFactory {

        public InBoxActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAdminSupportCommand());
        }
    }
}
