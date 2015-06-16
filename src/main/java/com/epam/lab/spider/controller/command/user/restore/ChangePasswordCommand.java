package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Orest Dzyuba on 15.06.2015.
 */
public class ChangePasswordCommand implements ActionCommand {

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService service = factory.create(UserService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (password.equals(confirmPassword)) {
            User user = service.getByEmail(email);
            if (user != null) {
                user.setPassword(new HashMD5().hash(password));
                service.update(user.getId(), user);
                response.sendRedirect("/login");
                return;
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("email", email);
            request.setAttribute("changeMessage", "Passwords don't match");
            request.getRequestDispatcher("jsp/user/pwrestore_newpw.jsp").forward(request, response);
        }
    }
}
