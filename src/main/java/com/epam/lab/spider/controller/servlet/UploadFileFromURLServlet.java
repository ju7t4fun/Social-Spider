package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.UploadFileFromURLCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 20.06.2015.
 */
public class UploadFileFromURLServlet extends HttpServlet {

    private static ActionFactory factory = new UploadFileFromURLFactory();

    private static class UploadFileFromURLFactory extends ActionFactory {

        public UploadFileFromURLFactory() {
            commands = new HashMap<>();
            commands.put("uploadurl", new UploadFileFromURLCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
