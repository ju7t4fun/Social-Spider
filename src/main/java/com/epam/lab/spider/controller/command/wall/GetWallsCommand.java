package com.epam.lab.spider.controller.command.wall;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.*;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

/**
 * Created by shell on 6/27/2015.
 */
public class GetWallsCommand implements ActionCommand {
    public static final ServiceFactory factory = ServiceFactory.getInstance();


    OwnerService ownerService = new OwnerService();
    ProfileService profileService = new ProfileService();

    TaskService taskService = factory.create(TaskService.class);
    WallService wallService =  factory.create(WallService.class);
    public static final Logger LOG = Logger.getLogger(GetWallsCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userObject = request.getSession().getAttribute("user");
        User user = null;
        if(userObject!=null && userObject instanceof User)user = (User) userObject;
        if(user==null) {
            response.sendError(401);
            return;
        }
        JSONArray jsonResponse = new JSONArray();
        JSONObject jsonRow;
        String search = request.getParameter("search");
        boolean activeFilter = false;
        if(search!=null && !search.isEmpty() ){
           activeFilter = true;
        }
        {
            List<Wall> walls = wallService.getAllByUserID(user.getId());
            for(Wall wall:walls){
                wall.getOwner().getVk_id();
                if(activeFilter){
                    if(wall.getOwner().getName().contains(search) ||
                            wall.getOwner().getDomain().contains(search));
                    else continue;
                }
                jsonRow = new JSONObject();
                jsonRow.put("value", wall.getId());
                jsonRow.put("text", wall.getOwner().getName());
                jsonResponse.add(jsonRow);
            }
        }
        String result = jsonResponse.toString();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        return;

    }
}
