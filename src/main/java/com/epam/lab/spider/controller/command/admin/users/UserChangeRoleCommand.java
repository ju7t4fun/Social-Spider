package com.epam.lab.spider.controller.command.admin.users;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 11.07.2015.
 */
public class UserChangeRoleCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            json.put("msg", "Успішно змінено");
        } else {
            json.put("status", "error");
            json.put("msg", "Відбулася помилка");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
