package com.epam.lab.spider.controller.command.notf;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.persistence.service.EventService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class RemoveNotificationCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static EventService service = factory.create(EventService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        JSONObject json = new JSONObject();
        if (service.delete(id)) {
            json.put("status", "success");
            json.put("msg", "Event removed successfully");
        } else {
            json.put("status", "error");
            json.put("msg", "An error occurred while deleting");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }

}
