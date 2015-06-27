package com.epam.lab.spider.controller.command.notf;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Event;
import com.epam.lab.spider.model.db.service.EventService;
import com.epam.lab.spider.model.db.service.MessageService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 26.06.2015.
 */
public class ShowNotificationCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static EventService service = factory.create(EventService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
       // Integer id = (Integer) session.getAttribute("user_id");
        List<Event> events = service.getByUserId(1);
        request.setAttribute("events", events);
        request.getRequestDispatcher("../jsp/user/notification.jsp").forward(request, response);
    }
}
