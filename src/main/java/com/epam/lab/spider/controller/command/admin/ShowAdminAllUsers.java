package com.epam.lab.spider.controller.command.admin;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Orest Dzyuba on 17.06.2015.
 */
public class ShowAdminAllUsers implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = factory.create(UserService.class);

        List<User> list = userService.getAll();
        for(int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
        request.getSession().setAttribute("listUsers",list);

        request.getRequestDispatcher("/jsp/admin/admin_allusers.jsp").forward(request, response);
    }
}
