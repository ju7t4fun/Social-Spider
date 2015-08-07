package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.UserHasCategory;
import com.epam.lab.spider.persistence.service.CategoryService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Dzyuba Orest
 */
public class AddRemoveUserCategories implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(AddRemoveUserCategories.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int categoryId = Integer.parseInt(request.getParameter("id"));
        String toChosen = request.getParameter("tochosen");
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        CategoryService categoryService = ServiceFactory.getInstance().create(CategoryService.class);
        LOG.debug("url : " + categoryService.getById(categoryId).getImageUrl());
        JSONObject json = new JSONObject();
        if (toChosen.equals("yes")) {
            if (categoryService.insertIntoUserHasCategories(new UserHasCategory(userId, categoryId))) {
                    json.put("status", "success");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.category")) + categoryId + UTF8.encoding(bundle.getString("notification.added")));

            } else {

                json.put("status", "error");
                json.put("msg", UTF8.encoding(bundle.getString("notification.add.category.error")));
            }
        }  else {
            if (categoryService.deleteUserCategory(new UserHasCategory(userId, categoryId))) {
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
