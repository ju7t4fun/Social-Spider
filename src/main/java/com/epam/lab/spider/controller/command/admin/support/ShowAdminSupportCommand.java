package com.epam.lab.spider.controller.command.admin.support;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Message;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.MessageService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Boyarsky Vitaliy
 */
public class ShowAdminSupportCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static UserService service = factory.create(UserService.class);
    private static MessageService messageService = factory.create(MessageService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = service.getAllUser();
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = service.getById(id);
            messageService.markAsReadAdminByUserId(id);
            List<Message> messages = messageService.getByUserId(id);
            request.setAttribute("current_user", user);
            request.setAttribute("messages", messages);
        }
        Map<Integer, Integer> count = messageService.getListUnReadAdminMessages(users.size());
        Map<User, Integer> map = new TreeMap<>();
        for (User user : users) {
            map.put(user, count.containsKey(user.getId()) ? count.get(user.getId()) : 0);
        }
        request.setAttribute("users", map);
        request.getRequestDispatcher("../jsp/admin/admin_support.jsp").forward(request, response);
    }
}
