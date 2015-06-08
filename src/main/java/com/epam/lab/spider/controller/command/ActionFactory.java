package com.epam.lab.spider.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public abstract class ActionFactory {

    protected Map<String, ActionCommand> commands = null;

    public ActionCommand action(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        return commands.get(action);
    }

}
