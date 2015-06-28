package com.epam.lab.spider.controller.servlet.wall;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.wall.GetWallsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by shell on 6/27/2015.
 */
public class WallServlet extends HttpServlet {
    private static ActionFactory factory = new WallActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class WallActionFactory extends ActionFactory {

        public WallActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new GetWallsCommand());
        }

    }
}
