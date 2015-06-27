package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.service.CategoryService;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 27.06.2015.
 */
public class CategoriesCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        List<Category> listCategory = new ArrayList<>();
        try {
            listCategory = categoryService.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray(listCategory);
        System.out.println(jsonArray);
        response.getWriter().print(jsonArray);
    }
}
