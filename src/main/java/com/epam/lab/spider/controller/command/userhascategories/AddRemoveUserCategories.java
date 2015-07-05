package com.epam.lab.spider.controller.command.userhascategories;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.UserHasCategory;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Орест on 7/5/2015.
 */
public class AddRemoveUserCategories implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int categoryId = Integer.parseInt(request.getParameter("id"));
        String tochosen = request.getParameter("tochosen");
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        CategoryService serv = ServiceFactory.getInstance().create(CategoryService.class);
        System.out.println("url : " + serv.getById(categoryId).getImageUrl());
        JSONObject json = new JSONObject();
        if (tochosen.equals("yes")) {
            if (serv.insertIntoUserHasCategories(new UserHasCategory(userId, categoryId))) {
                    json.put("status", "success");
                    json.put("msg", "Add " + categoryId + " to user_has_categories");

            } else {

                json.put("status", "error");
                json.put("msg", "Error");
            }
        }  else {
            if (serv.deleteUserCategory(new UserHasCategory(userId, categoryId))) {
                json.put("status", "success");
                json.put("msg", "Remove " + categoryId + " from user_has_categories");
            } else {

                json.put("status", "error");
                json.put("msg", "Error");
            }
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
