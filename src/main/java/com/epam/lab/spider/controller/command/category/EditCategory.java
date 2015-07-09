package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Орест on 7/9/2015.
 */
public class EditCategory implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("category");

        System.out.println("im in!!!");
        JSONObject json = new JSONObject();

        try {
            int id = Integer.parseInt(request.getParameter("catIdEdit"));
            Category category = service.getById(id);
           if (name!=null) {
               category.setName(name);
           }
            String newImgUrl = (String) request.getSession().getAttribute("urlCat");
            if (newImgUrl!=null) {
                category.setImageUrl(newImgUrl);
            }
            service.update(id, category);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
