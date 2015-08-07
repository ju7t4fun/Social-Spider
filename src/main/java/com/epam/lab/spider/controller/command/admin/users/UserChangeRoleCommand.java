package com.epam.lab.spider.controller.command.admin.users;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class UserChangeRoleCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int id = Integer.parseInt(request.getParameter("id"));
        UserService service = factory.create(UserService.class);
        User user = service.getById(id);
        switch (user.getRole()) {
            case ADMIN:
                user.setRole(User.Role.USER);
                break;
            case USER:
                user.setRole(User.Role.ADMIN);
        }
        JSONObject json = new JSONObject();
        if (service.update(user.getId(), user)) {
            json.put("status", "success");
            json.put("msg", UTF8.encoding(bundle.getString("notification.change.role.success")));
        } else {
            json.put("status", "error");
            json.put("msg", UTF8.encoding(bundle.getString("notification.change.role.error")));
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
