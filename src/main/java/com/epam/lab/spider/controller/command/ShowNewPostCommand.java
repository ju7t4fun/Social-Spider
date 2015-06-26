package com.epam.lab.spider.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marian Voronovskyi on 25.06.2015.
 */
public class ShowNewPostCommand implements  ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> urlType = new HashMap<>();
        request.getSession().setAttribute("files_url", urlType);
        request.getRequestDispatcher("jsp/post/addpost.jsp").forward(request, response);
    }
}
