package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.UploadImageCommand;
import com.epam.lab.spider.controller.command.user.accounts.AddAccountCommand;
import com.epam.lab.spider.controller.command.user.accounts.ShowAccountsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public class UploadImageServlet extends HttpServlet {

    private static ActionFactory factory = new UploadImageFactory();

    private static class UploadImageFactory extends ActionFactory {

        public UploadImageFactory() {
            commands = new HashMap<>();
            commands.put("upload", new UploadImageCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
