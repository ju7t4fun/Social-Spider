package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
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
            wallMap.put("text", wall.getOwner().getName());
            sourceWalls.add(wallMap);
        }
        request.setAttribute("sourceWalls",sourceWalls);
        List<Map<String,String>> destinationWalls = new ArrayList<>();
        for (Wall wall : walls) {
            if(wall.getPermission()!= Wall.Permission.WRITE)continue;
            wallMap = new HashMap<>();
            wallMap.put("id", wall.getId().toString());
            wallMap.put("text", wall.getOwner().getName());
            destinationWalls.add(wallMap);
        }
        request.setAttribute("destinationWalls",destinationWalls);
        request.getRequestDispatcher("jsp/post/addtask.jsp").forward(request, response);
    }
}
