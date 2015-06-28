package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shell on 6/26/2015.
 */
public class ShowAllTasksCommand implements ActionCommand {

    public static final ServiceFactory factory = ServiceFactory.getInstance();


    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();

    TaskService taskService = factory.create(TaskService.class);
    WallService wallService =  factory.create(WallService.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userObject = request.getSession().getAttribute("user");
        User user = null;
        if(userObject!=null && userObject instanceof User)user = (User) userObject;

        if(user!=null) {
            List<Task> tasks = taskService.getByUserId(user.getId());
            request.setAttribute("taskCount", tasks.size());
            List<Map<String, String>> tasksResult = new ArrayList<>();
            for (Task task:tasks) {
                //id
                Map<String, String> property = new HashMap<>();
                property.put("id", task.getId().toString());
                //source
                StringBuilder sourceStringBuilder = new StringBuilder();
                List<Wall> sourceWalls = wallService.getSourceByTaskId(task.getId());
                for (Wall wall : sourceWalls) {
                    String vkOwnerId =
                            wall.getOwner().getVk_id().toString();
                    sourceStringBuilder.append(vkOwnerId).append(", ");
                }
                String source = "";
                try {
                source = sourceStringBuilder.substring(0 , sourceStringBuilder.length()-2);
                }catch (StringIndexOutOfBoundsException x){
                }
                property.put("source", source);
                //destination
                StringBuilder destinationStringBuilder = new StringBuilder();
                List<Wall> destinationWalls = wallService.getDestinationByTaskId(task.getId());
                for (Wall wall : destinationWalls) {
                    String vkOwnerId =
                    wall.getOwner().getVk_id().toString();
                    destinationStringBuilder.append(vkOwnerId).append(", ");
                }
                String destination = "";
                try {
                    destination = destinationStringBuilder.substring(0, destinationStringBuilder.length() - 2);
                }catch (StringIndexOutOfBoundsException x){
                }
                property.put("destination", destination);
                //type
                property.put("type", task.getType().toString());
                //content
                property.put("contenttype", task.getContentType().toString());
                //start time
                if(task.getStartTimeType() == Task.StartTimeType.INTERVAL){
                    property.put("time","Every "+task.getIntervalMin()+" .. "+task.getIntervalMax()+" minutes.");
                }else{
                    property.put("time","unsupported");
                }
                // workLimit
                if(task.getWorkTimeLimit()== Task.WorkTimeLimit.ROUND_DAILY){
                    property.put("workLimit","Unlimited");
                }else{
                    property.put("workLimit","Unsupported");
                }
                //active
                if(task.getState()== Task.State.RUNNING){
                    property.put("active","RUNNING");
                }

                tasksResult.add(property);
            }
            request.setAttribute("tasks", tasksResult);
            request.getRequestDispatcher("jsp/post/alltasks.jsp").forward(request, response);
        }
    }
}
