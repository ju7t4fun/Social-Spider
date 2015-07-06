package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.entity.UserHasCategory;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Орест on 7/5/2015.
 */
public class AddRemoveUserCategories implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int categoryId = Integer.parseInt(request.getParameter("id"));
        String tochosen = request.getParameter("tochosen");
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        CategoryService serv = ServiceFactory.getInstance().create(CategoryService.class);
        System.out.println("url : " + serv.getById(categoryId).getImageUrl());
        JSONObject json = new JSONObject();
        if (tochosen.equals("yes")) {
            if (serv.insertIntoUserHasCategories(new UserHasCategory(userId, categoryId))) {
                    json.put("status", "success");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.category")) + categoryId + UTF8.encoding(bundle.getString("notification.added")));

            } else {

                json.put("status", "error");
                json.put("msg", UTF8.encoding(bundle.getString("notification.add.category.error")));
            }
        }  else {
            if (serv.deleteUserCategory(new UserHasCategory(userId, categoryId))) {
                json.put("status", "success");
                json.put("msg", UTF8.encoding(bundle.getString("notification.category")) + categoryId + UTF8.encoding(bundle.getString("notification.removed")));
            } else {

                json.put("status", "error");
                json.put("msg", UTF8.encoding(bundle.getString("notification.remove.category.error")));
            }
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
