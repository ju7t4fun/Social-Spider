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

        String toSearch = request.getParameter("sSearch");
        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");

        int start = 0;
        int ammount = 5;

        if (pageNo!=null) {
            start = Integer.parseInt(pageNo);
            if (start < 0) {
                start = 0;
            }
        }

        if (pageSize!=null) {
            ammount = Integer.parseInt(pageSize);
            if (ammount < 5) {
                ammount = 5;
            }
        }

        int totalCount = 0;
        List<Category> categories;
        if (toSearch == null || toSearch == "") {
            categories = service.getAllWithLimit(start,ammount);
            totalCount = service.getCountAll();
        } else {
            toSearch = "%" + toSearch + "%";
            System.out.println("toSearch: " + toSearch);
            categories = service.getAllWithSearchLimited(toSearch, start, ammount);
            totalCount = service.getCountAllWithSearch(toSearch);
        }
        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();
        for (Category category : categories) {
            JSONArray row = new JSONArray();
            row.put(category.getId());
            row.put(category.getName()+ "|" + category.getImageUrl());
            row.put(category.getId());
            table.put(row);
        }
        System.out.println(categories.size());

        result.put("iTotalDisplayRecords", totalCount);
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }
}
