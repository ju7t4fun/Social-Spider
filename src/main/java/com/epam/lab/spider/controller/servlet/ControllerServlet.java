package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.controller.UploadFileCommand;
import com.epam.lab.spider.controller.command.controller.UploadFileFromURLCommand;
import com.epam.lab.spider.controller.command.controller.LocaleCommand;
import com.epam.lab.spider.controller.command.controller.CategoriesCommand;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class ApiActionFactory extends ActionFactory {

        public ApiActionFactory() {
            commands = new HashMap<>();
            commands.put("locale", new LocaleCommand());
            commands.put("upload", new UploadFileCommand());
            commands.put("uploadurl", new UploadFileFromURLCommand());
            commands.put("categories", new CategoriesCommand());
        }
    }

}
