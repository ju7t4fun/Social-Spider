package com.epam.lab.spider.controller.command.notf;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.service.MessageService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 26.06.2015.
 */
public class ShowNotificationCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static MessageService service = factory.create(MessageService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ///
//        service.getByUserId()
        request.getRequestDispatcher("../jsp/user/notification.jsp").forward(request, response);
    }
}
