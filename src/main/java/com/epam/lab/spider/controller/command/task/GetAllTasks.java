package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;
import com.epam.lab.spider.persistence.service.WallService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Dzyuba Orest
 */
public class GetAllTasks implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int start = Integer.parseInt(request.getParameter("iDisplayStart"));
        int size = Integer.parseInt(request.getParameter("iDisplayLength"));

        TaskService service = ServiceFactory.getInstance().create(TaskService.class);
        WallService wallService = ServiceFactory.getInstance().create(WallService.class);

        List<Task> tasks;
        int tasksCount;

        User user;

        try {
            user = (User) request.getSession().getAttribute("user");
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        String taskType = request.getParameter("taskType");
        if (user.getRole() == User.Role.ADMIN && taskType != null && taskType.equals("forAdmin")) {
            tasks = service.getAllLimitedAdmin(start, size);
            tasksCount = service.getCountAdmin();
        } else {

            String type = request.getParameter("type");
            if (type != null && type.equals("active")) {
                tasks = service.getAllActiveLimited(user.getId(), start, size);
                tasksCount = service.getActiveCount(user.getId());
            } else {
                tasks = service.getAllLimited(user.getId(), start, size);
                tasksCount = service.getCount(user.getId());
            }
        }

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();

        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                JSONArray row = new JSONArray();

                try {
                    //source

                    StringBuilder sourceStringBuilder = new StringBuilder();
                    List<Wall> sourceWalls = wallService.getSourceByTaskId(task.getId());
                    for (Wall wall : sourceWalls) {
                        String vkOwnerId = wall.getOwner().getName();
                        sourceStringBuilder.append(vkOwnerId).append(", ");
                    }
                    String source = "";
                    try {
                        source = sourceStringBuilder.substring(0, sourceStringBuilder.length() - 2);
                    } catch (StringIndexOutOfBoundsException x) {
                    }
                    row.put(source);

                    //destination
                    StringBuilder destinationStringBuilder = new StringBuilder();
                    List<Wall> destinationWalls = wallService.getDestinationByTaskId(task.getId());
                    for (Wall wall : destinationWalls) {
                        String vkOwnerId =
                                wall.getOwner().getName().toString();
                        destinationStringBuilder.append(vkOwnerId).append(", ");
                    }
                    String destination = "";
                    try {
                        destination = destinationStringBuilder.substring(0, destinationStringBuilder.length() - 2);
                    } catch (StringIndexOutOfBoundsException x) {
                    }

                    row.put(destination);

                    //type

                    row.put(task.getType().toString());

                    //content

                    row.put(task.getContentType().toString());

                    //state
                    if (task.getState() == Task.State.RUNNING) {
                        row.put("running");
                    } else {
                        row.put("stopped");
                    }
                    //id for delete button
                    row.put(task.getId());

                    array.put(row);
                } catch (Exception ex) {
                }
            }
        }

        result.put("iTotalDisplayRecords", tasksCount);
        result.put("aaData", array);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }
}
