package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.AdminAddTaskCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Marian Voronovskyi
 */
public class AdminAddTaskServlet extends HttpServlet {
    private static ActionFactory factory = new AdminAddTaskFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    private static class AdminAddTaskFactory extends ActionFactory {
        public AdminAddTaskFactory() {
            commands = new HashMap<>();
            commands.put("default", new AdminAddTaskCommand());
        }
    }
}
