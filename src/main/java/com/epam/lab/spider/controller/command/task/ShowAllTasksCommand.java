package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.persistence.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yura Kovalik
 */
public class ShowAllTasksCommand implements ActionCommand {

    public static final ServiceFactory factory = ServiceFactory.getInstance();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            request.getRequestDispatcher("jsp/post/alltasks.jsp").forward(request, response);

    }
}
