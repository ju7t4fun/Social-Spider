package com.epam.lab.spider.controller.command.api;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class AddAccountCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("token", request.getParameter("token"));
        session.setAttribute("userId", request.getParameter("user_id"));
        response.sendRedirect("/user/accounts");
    }

}
