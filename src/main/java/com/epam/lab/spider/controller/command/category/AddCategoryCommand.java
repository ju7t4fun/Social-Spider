package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.impl.CategoryImpl;
import com.epam.lab.spider.persistence.service.CategoryService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marian Voronovskyi
 */
public class AddCategoryCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("category");
        name = ReplaceHtmlTags.reaplaceAll(name);
        if (name.split("\\|").length != 2) {
            json.put("status", "warning");
            json.put("msg", "Неправильний формат категорії (name_ua|name_en)");
            response.getWriter().write(json.toString());
            return;
        }
        Category category = new CategoryImpl();
        category.setName(name);
        if (request.getSession().getAttribute("urlCat") != null)
            category.setImageUrl((String) request.getSession().getAttribute("urlCat"));
        if (service.insert(category)) {
            json.put("status", "success");
            json.put("msg", "Successfully added");
        } else {
            json.put("status", "error");
            json.put("msg", "Occurred error while updated!");
        }
        request.getSession().removeAttribute("urlCat");
        response.getWriter().write(json.toString());
    }


}
