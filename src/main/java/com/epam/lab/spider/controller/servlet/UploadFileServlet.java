package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.controller.UploadFileCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public class UploadFileServlet extends HttpServlet {

    private static ActionFactory factory = new UploadFileFactory();

    private static class UploadFileFactory extends ActionFactory {

        public UploadFileFactory() {
            commands = new HashMap<>();
            commands.put("upload", new UploadFileCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
