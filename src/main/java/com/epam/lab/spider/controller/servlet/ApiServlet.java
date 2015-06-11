package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.api.AddAccountCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class ApiServlet extends HttpServlet {

    private static ActionFactory factory = new ApiActionFactory();

    private static class ApiActionFactory extends ActionFactory {

        public ApiActionFactory() {
            commands = new HashMap<String, ActionCommand>();
            commands.put("add", new AddAccountCommand());
        }

        @Override
        public void action(HttpServletRequest request, HttpServletResponse response) throws IOException,
                ServletException {
            String action = null;
            String[] args = request.getRequestURI().split("/");
            if (args[2].equals("method")) {
                action = args[3];
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            commands.get(action).execute(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

}
