package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 11.06.2015.
 */
public class SignInCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        System.out.println("rgtgecrh");
        String password = request.getParameter("password");
        boolean isAuth = false;
        boolean isActive = false;
        if (isAuth) {
            if (!isActive) {
                request.getSession().setAttribute("loginMessage","Your account is non-activated." +
                        "Please activate your account via email");
                request.getSession().setAttribute("login", email);
                response.sendRedirect("/login");
                return;
            }
            //needtoDO: add to session neccessery variables
            response.sendRedirect("/");
            return;
        } else {
            System.out.println(email);
            request.getSession().setAttribute("loginMessage", "wrong username or password");
            request.getSession().setAttribute("login", email);
            response.sendRedirect("/login");

            return;
        }
    }

}
