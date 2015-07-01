package com.epam.lab.spider.controller.servlet.admin;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.admin.CategoriesCommand;
import com.epam.lab.spider.controller.command.category.AddCategoryCommand;
import com.epam.lab.spider.controller.command.category.GetCategoryCommand;
import com.epam.lab.spider.controller.command.category.RemoveCategoryCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Marian Voronovskyi on 29.06.2015.
 */
public class CategoriesServlet extends HttpServlet {
    private static ActionFactory factory = new CategoriesFactory();

    private static class CategoriesFactory extends ActionFactory {

        public CategoriesFactory() {
            commands = new HashMap<>();
            commands.put("default", new CategoriesCommand());
            commands.put("getcategory", new GetCategoryCommand());
            commands.put("addcategory", new AddCategoryCommand());
            commands.put("removecategory", new RemoveCategoryCommand());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        factory.action(request, response);
    }
}
