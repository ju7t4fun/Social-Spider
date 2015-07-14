package com.epam.lab.spider.controller.command.admin.users;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Орест on 22.06.2015.
 */
public class UserStatusChangerCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        UserService userService = ServiceFactory.getInstance().create(UserService.class);
        User user = userService.getByEmail(email);
        if (user != null) {
            user.setState(User.State.valueOf(status.toUpperCase()));
            JSONObject json = new JSONObject();
            if (userService.update(user.getId(), user)) {
                json.put("status", "success");
                json.put("msg", UTF8.encoding(bundle.getString("notification.change.status.success")));
            } else {
                json.put("status", "error");
                json.put("msg", UTF8.encoding(bundle.getString("notification.change.status.success")));
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        }
    }
}
