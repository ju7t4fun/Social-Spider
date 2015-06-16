package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.UploadCommand;
import com.epam.lab.spider.controller.command.UploadImageCommand;
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
            commands = new HashMap<>();
            commands.put("locale", new LocaleCommand());
            commands.put("uploadImage.", new UploadImageCommand());
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
