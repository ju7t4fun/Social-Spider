package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Orest Dzyuba on 15.06.2015.
 */
public class ChangePasswordCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");

        if (password.equals(repeatPassword)) {

            UserService userService = new UserService();
            User user = userService.getByEmail(email);
            if ( user != null) {
                user.setPassword(new HashMD5().hash(password));
                userService.update(user.getId(),user);
            }
            response.sendRedirect("/");
            return;
        } else {
            request.setAttribute("email",email);
            request.setAttribute("changeMessage", "Passwords dont match");
            request.getRequestDispatcher("/forgotpassword?action=showforgotinput").forward(request, response);
            return;
        }
    }
}
