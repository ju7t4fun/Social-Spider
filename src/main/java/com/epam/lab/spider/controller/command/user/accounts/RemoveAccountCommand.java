package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 18.06.2015.
 */
public class RemoveAccountCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        service.delete(id);
    }

}
