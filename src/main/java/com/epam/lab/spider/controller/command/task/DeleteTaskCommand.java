package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO АХУЄТЬ А ДАВАЙТЕ Я З ПІД НЕЗАЛОГОВАНОГО ЮЗЕРА ВИДАЛЮ ВСІ ТАСКИ
        String strId = request.getParameter("taskId");

        if (strId!=null) {
            try {
                TaskService service = ServiceFactory.getInstance().create(TaskService.class);

                service.delete(Integer.parseInt(strId));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("alert","Deleting has finished!");
        String result = resultJson.toString();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        return;
    }
}
