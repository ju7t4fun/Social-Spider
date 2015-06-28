package com.epam.lab.spider.controller.servlet.admin.ajax;

import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import com.mchange.v2.sql.filter.SynchronizedFilterStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Orest Dzyuba on 17.06.2015.
 */
public class StatusChangerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in servlet!!!");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        UserService userService = ServiceFactory.getInstance().create(UserService.class);
        User user = userService.getByEmail(email);
        if (user!=null) {
            user.setState(User.State.valueOf(status.toUpperCase()));
            userService.update(user.getId(),user);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
