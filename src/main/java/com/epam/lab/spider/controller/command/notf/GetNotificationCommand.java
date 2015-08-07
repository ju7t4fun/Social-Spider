package com.epam.lab.spider.controller.command.notf;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Event;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.persistence.service.EventService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class GetNotificationCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static EventService service = factory.create(EventService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int page = Integer.parseInt(request.getParameter("iDisplayStart"));
        int size = Integer.parseInt(request.getParameter("iDisplayLength"));
        List<Event> events = service.getByUserId(user.getId(), page, size);
        int count = service.getCountByUserId(user.getId());

        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();
        for (Event event : events) {
            JSONArray row = new JSONArray();
            row.put(event.getType().toString());
            row.put(event.getTitle());
            row.put(event.getMessage());
            row.put(event.getTime());
            row.put(event.getId());
            table.put(row);
        }

        result.put("iTotalDisplayRecords", count);
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }

}
