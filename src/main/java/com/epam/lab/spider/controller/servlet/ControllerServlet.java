package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.category.AddCategoryCommand;
import com.epam.lab.spider.controller.command.category.GetCategoryCommand;
import com.epam.lab.spider.controller.command.controller.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Boyarsky Vitaliy
 */
public class ControllerServlet extends HttpServlet {

    private static ActionFactory factory = new ApiActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class ApiActionFactory extends ActionFactory {

        public ApiActionFactory() {
            commands = new HashMap<>();
            commands.put("locale", new LocaleCommand());
            commands.put("unlock", new UnlockCommand());
            commands.put("upload", new UploadFileCommand());
            commands.put("uploadurl", new UploadFileFromURLCommand());
            commands.put("categories", new CategoriesCommand());
            commands.put("getcategory", new GetCategoryCommand());
            commands.put("addcategory", new AddCategoryCommand());
            commands.put("getLangJSON", new GetLangJSONCommand());
        }
    }

}
