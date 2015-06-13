package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.hash.HashMD5;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 11.06.2015.
 */
public class SignInCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        UserService serv = new UserService();
        User currUser = null;
        List<User> users = serv.getAll();
        if (users!=null) {
            for (int i = 0; i < users.size(); ++i) {

                if (users.get(i).getEmail().equalsIgnoreCase(email) && !users.get(i).getDeleted()) {
                    currUser = users.get(i);
                }

            }
        }


        if (currUser != null) {

            boolean isActive = currUser.getConfirm();
            boolean isAuth = false;
            HashMD5 hashHelper = new HashMD5();
            if (hashHelper.hash(password).equals(hashHelper.hash(currUser.getPassword()))) {
                isAuth = true;
            }

            if (isAuth) {
                if (!isActive) {
                    request.getSession().setAttribute("loginMessage", "Your account is non-activated." +
                            "Please activate your account via email");
                    request.getSession().setAttribute("login", email);
                    response.sendRedirect("/login");
                    return;
                }
                request.getSession().setAttribute("user",currUser);
                response.sendRedirect("/");
                return;
            } else {

                request.getSession().setAttribute("loginMessage", "wrong username or password");
                request.getSession().setAttribute("login", email);
                response.sendRedirect("/login");

                return;
            }
        } else {

            request.getSession().setAttribute("loginMessage", "There is no such a user!");
            request.getSession().setAttribute("login", email);
            response.sendRedirect("/login");

            return;

        }
    }

}
