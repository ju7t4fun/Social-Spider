package com.epam.lab.spider.controller.servlet.task;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.task.*;
import com.epam.lab.spider.controller.command.wall.GetWallsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by shell on 6/26/2015.
 */
public class TaskServlet extends HttpServlet {
    private static ActionFactory factory = new TaskActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class TaskActionFactory extends ActionFactory {

        public TaskActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowAllTasksCommand());
            commands.put("create", new CreateTaskCommand());
            commands.put("save", new SaveTaskCommand());

            commands.put("stateChange", new StateChangeTaskCommand());
            commands.put("gettasks", new GetAllTasks());
            commands.put("deletetask", new DeleteTaskCommand());
        }

    }
}
