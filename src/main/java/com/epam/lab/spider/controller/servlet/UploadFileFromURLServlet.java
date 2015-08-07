package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.controller.UploadFileFromURLCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Marian Voronovskyi
 */
public class UploadFileFromURLServlet extends HttpServlet {

    private static ActionFactory factory = new UploadFileFromURLFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    private static class UploadFileFromURLFactory extends ActionFactory {

        public UploadFileFromURLFactory() {
            commands = new HashMap<>();
            commands.put("uploadurl", new UploadFileFromURLCommand());
        }
    }
}
