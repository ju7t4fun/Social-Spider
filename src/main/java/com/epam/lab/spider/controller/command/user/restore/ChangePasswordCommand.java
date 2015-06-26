package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashMD5;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

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
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (password.equals(confirmPassword)) {
            User user = service.getByEmail(email);
            if (user != null) {
                user.setPassword(new HashMD5().hash(password));
                service.update(user.getId(), user);
                session.setAttribute("toastr_notification", "success|" + UTF8.encoding(bundle.getString("restore" +
                        ".notification.success")));
                response.sendRedirect("/login");
                return;
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("email", email);
            request.setAttribute("toastr_notification", "error|" + UTF8.encoding(bundle.getString("restore" +
                    ".notification.match")));
            request.getRequestDispatcher("jsp/user/pwrestore_newpw.jsp").forward(request, response);
        }
    }
}
