package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shell on 6/26/2015.
 */
public class ShowAllTasksCommand implements ActionCommand {

    public static final ServiceFactory factory = ServiceFactory.getInstance();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            request.getRequestDispatcher("jsp/post/alltasks.jsp").forward(request, response);

    }
}
