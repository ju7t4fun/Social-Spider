package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Boyarsky Vitaliy
 */
public class ShowCreatedPostCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            // Формування дати
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
            Date now = new Date();
            String currentDate = date.format(now);
            String currentTime = time.format(now);
            request.setAttribute("date", currentDate);
            request.setAttribute("time", currentTime);

            now = new Date(now.getTime() + 60 * 60 * 1000);
            currentDate = date.format(now);
            currentTime = time.format(now);
            request.setAttribute("del_date", currentDate);
            request.setAttribute("del_time", currentTime);
        }
        request.getRequestDispatcher("jsp/post/created.jsp").forward(request, response);
    }
}
