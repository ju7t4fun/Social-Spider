package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class ShowPostedPostCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/post/postedposts.jsp").forward(request, response);
    }
}
