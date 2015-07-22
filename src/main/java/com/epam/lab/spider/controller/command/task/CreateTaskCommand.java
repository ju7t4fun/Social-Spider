package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
public class CreateTaskCommand implements ActionCommand {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    WallService wallService =  factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Object userObject = request.getSession().getAttribute("user");
        User user = null;
        if(userObject!=null && userObject instanceof User)user = (User) userObject;
        if(user==null) {
            response.sendError(401);
            return;
        }
        List<Wall> walls = wallService.getAllByUserID(user.getId());
        Map<String, String> wallMap;
        List<Map<String,String>> sourceWalls = new ArrayList<>();
        for (Wall wall : walls) {
            if(wall.getPermission()!= Wall.Permission.READ)continue;
            wallMap = new HashMap<>();
            wallMap.put("id", wall.getId().toString());
            wallMap.put("selected", "false");
            wallMap.put("text", wall.getOwner().getName()+"("+wall.getProfile().getName()+")");
            sourceWalls.add(wallMap);
        }
        request.setAttribute("sourceWalls",sourceWalls);
        List<Map<String,String>> destinationWalls = new ArrayList<>();
        for (Wall wall : walls) {
            if(wall.getPermission()!= Wall.Permission.WRITE)continue;
            wallMap = new HashMap<>();
            wallMap.put("id", wall.getId().toString());
            wallMap.put("selected", "false");
            wallMap.put("text", wall.getOwner().getName()+"("+wall.getProfile().getName()+")");
            destinationWalls.add(wallMap);
        }
        request.setAttribute("destinationWalls",destinationWalls);


        request.setAttribute("task_id","0");
        request.setAttribute("posting_type",Task.Type.COPY);
        request.setAttribute("grabbing_type",Task.GrabbingType.BEGIN);
        request.setAttribute("repeat", Task.Repeat.REPEAT_DISABLE);
        request.setAttribute("repeat_count", "10");

        request.setAttribute("signature", "");
        request.setAttribute("hashtags", "socialspider");
        request.setAttribute("start_time",Task.StartTimeType.INTERVAL);
        request.setAttribute("work_time", Task.WorkTimeLimit.ROUND_DAILY);

        request.setAttribute("interval_min", "5");
        request.setAttribute("interval_max", "12");
        request.setAttribute("post_count", "1");
        request.setAttribute("post_delay_min", "40");
        request.setAttribute("post_delay_max", "80");
        request.setAttribute("grabbing_mode", Task.GrabbingMode.TOTAL);

        request.setAttribute("likes", "60");
        request.setAttribute("reposts", "5");
        request.setAttribute("comments", "0");

        request.setAttribute("TEXT","true");
        request.setAttribute("PHOTO","true");
        request.setAttribute("AUDIO","true");
        request.setAttribute("VIDEO","true");
        request.setAttribute("DOCUMENTS","true");
        request.setAttribute("REPOSTS","true");



        request.setAttribute("likes_max","1200");
        request.setAttribute("reposts_max","600");
        request.setAttribute("comments_max","300");
        request.setAttribute("delay_limit","120");
        request.setAttribute("interval_limit","120");
        request.getRequestDispatcher("jsp/task/edit-task-master.jsp").forward(request, response);
    }
}
