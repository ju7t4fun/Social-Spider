package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzyuba Orest
 */
public class DeleteTaskCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static TaskService service = factory.create(TaskService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("taskId"));
        org.json.JSONObject json = new org.json.JSONObject();
        if (service.delete(id)) {
            json.put("status", "success");
            json.put("msg", "Successfully deleted!");
        } else {
            json.put("status", "error");
            json.put("msg", "Occured error while deleting!");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
