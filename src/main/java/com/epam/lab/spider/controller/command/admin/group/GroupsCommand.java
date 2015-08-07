package com.epam.lab.spider.controller.command.admin.group;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marian Voronovskyi
 */
public class GroupsCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/admin/admin_groups.jsp").forward(request, response);
    }
}
