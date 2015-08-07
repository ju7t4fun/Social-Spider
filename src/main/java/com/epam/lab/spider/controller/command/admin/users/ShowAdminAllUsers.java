package com.epam.lab.spider.controller.command.admin.users;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Dzyuba Orest
 */
public class ShowAdminAllUsers implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = factory.create(UserService.class);
        List<User> list = service.getAll();
        request.getSession().setAttribute("listUsers", list);
        request.getRequestDispatcher("/jsp/admin/admin_allusers.jsp").forward(request, response);
    }
}
