package com.epam.lab.spider.controller.command.admin.category;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marian Voronovskyi
 */
public class CategoriesCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/admin/admin_categories.jsp").forward(request, response);
    }

}
