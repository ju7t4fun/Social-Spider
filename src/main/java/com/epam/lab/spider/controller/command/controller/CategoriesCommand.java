package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.persistence.service.CategoryService;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marian Voronovskyi
 */
public class CategoriesCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(CategoriesCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        List<Category> listCategory = new ArrayList<>();
        try {
            listCategory = categoryService.getAll();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        JSONArray jsonArray = new JSONArray(listCategory);
        response.getWriter().print(jsonArray);
    }
}
