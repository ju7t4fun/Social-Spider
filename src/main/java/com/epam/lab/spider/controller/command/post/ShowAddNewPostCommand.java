package com.epam.lab.spider.controller.command.post;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marian Voronovskyi on 25.06.2015.
 */
public class ShowAddNewPostCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService service = factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> urlType = new HashMap<>();
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
        {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        }
        request.getSession().setAttribute("files_url", urlType);
        request.getRequestDispatcher("jsp/post/addpost.jsp").forward(request, response);
    }
}
