package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.category.AddRemoveUserCategories;
import com.epam.lab.spider.controller.command.category.ShowUserHsCategoriesCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Dzyuba Orest
 */
public class UserHasCategoriesServlet extends HttpServlet {
    private static UserHasCategoriesFactory factory = new UserHasCategoriesFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class UserHasCategoriesFactory extends ActionFactory {
        public UserHasCategoriesFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowUserHsCategoriesCommand());
            commands.put("addremove", new AddRemoveUserCategories());
        }
    }
}
