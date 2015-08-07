package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.group.GroupBanUnbanCommand;
import com.epam.lab.spider.controller.command.admin.group.GroupUniqueCommand;
import com.epam.lab.spider.controller.command.admin.group.GroupsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Marian Voronovskyi
 */
public class GroupsServlet extends HttpServlet {
    private static ActionFactory factory = new GroupsFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    private static class GroupsFactory extends ActionFactory {

        public GroupsFactory() {
            commands = new HashMap<>();
            commands.put("default", new GroupsCommand());
            commands.put("banunban", new GroupBanUnbanCommand());
            commands.put("loadunique", new GroupUniqueCommand());
        }
    }
}
