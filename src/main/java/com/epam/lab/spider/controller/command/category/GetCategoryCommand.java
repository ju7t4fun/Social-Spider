package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 29.06.2015.
 */
public class GetCategoryCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = service.getAll();
        System.out.println(categories);
        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();
        for (Category category : categories) {
            JSONArray row = new JSONArray();
            row.put(category.getId());
            row.put(category.getName());
            row.put(category.getId());
            table.put(row);
        }
      result.put("iTotalDisplayRecords", categories.size());
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }
}
