package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.ShowAdminAllUsers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 29.06.2015.
 */
public class ShowUsersServlet extends HttpServlet {
    private static ActionFactory factory = new ShowUsersFactory();

    private static class ShowUsersFactory extends ActionFactory {
        public ShowUsersFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAdminAllUsers());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
