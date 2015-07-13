package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.TaskService;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Орест on 7/7/2015.
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
            json.put("msg", "Успішно видалено");
        } else {
            json.put("status", "error");
            json.put("msg", "Відбулася помилка при видаленні");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
