package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.AdminOwnerCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 09.07.2015.
 */
public class AdminOwnerServlet extends HttpServlet {
    private static ActionFactory factory = new AdminOwnerFactory();

    private static class AdminOwnerFactory extends ActionFactory {
        public AdminOwnerFactory() {
            commands = new HashMap<>();
            commands.put("default", new AdminOwnerCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
