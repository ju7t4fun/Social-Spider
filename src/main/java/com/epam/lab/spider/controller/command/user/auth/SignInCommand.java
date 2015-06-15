package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 11.06.2015.
 */
public class SignInCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService us = factory.create(UserService.class);
    private static HashMD5 md5 = new HashMD5();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        // Перевірка логіна та паролю
        if (us.checkPassword(email, md5.hash(password))) {
            // Авторизація
            User user = us.getByEmail(email);

            boolean isActive = user.getConfirm();
            if (!isActive) {
                // Користувач не активний
                request.setAttribute("loginMessage", "Your account is non-activated. Please activate " +
                        "your account via email");
                request.setAttribute("login", email);
                request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                return;
            }
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/");
        } else {
            // Помилка авторизації
            request.setAttribute("loginMessage", "There is no such a user!");
            request.setAttribute("login", email);
            request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
        }
    }

}
