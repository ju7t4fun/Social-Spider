package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.ServerLocationUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class SignInCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService us = factory.create(UserService.class);
    private static HashMD5 md5 = new HashMD5();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        // Перевірка логіна та паролю
        if (us.checkPassword(email, md5.hash(password))) {
            // Авторизація
            User user = us.getByEmail(email);
            switch (user.getState()) {
                case CREATED:
                    // Користувач не активний
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.created")));
                    request.setAttribute("login", email);
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case BANNED:
                    // Користувач забанений
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.banned")));
                    request.setAttribute("login", email);
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case ACTIVATED:
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(ServerLocationUtils.getServerPath(request)+"/");
            }
        } else {
            // Помилка авторизації
            request.setAttribute("toastr_notification", "error|" + UTF8.encoding(bundle.getString("login.notification" +
                    ".error")));
            request.setAttribute("login", email);
            request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
        }
    }

}
