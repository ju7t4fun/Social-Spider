package com.epam.lab.spider.controller.command.user.signup;

import com.epam.lab.spider.ServerLocationUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.hash.HashSHA;
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
 * @author Dzyuba Orest
 */
public class ActivateCommand implements ActionCommand {

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.create(UserService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String email = request.getParameter("email");
        String hash = request.getParameter("hash");
        User user = userService.getByEmail(email);

        // Перевіряєм чи користувач існує
        if (user != null) {
            HashSHA sha = new HashSHA();
            // Перевіряє hash (createTime + state)
            if (hash.equals(sha.hash(user.getCreateTime().toString() + user.getState()))) {
                user.setState(User.State.ACTIVATED);
                userService.update(user.getId(), user);
                HttpSession session = request.getSession();
                ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
                session.setAttribute("toastr_notification", "success|" + UTF8.encoding(bundle.getString("reg.activate" +
                        ".success")));
                response.sendRedirect(ServerLocationUtils.getServerPath(request)+"/login");
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
