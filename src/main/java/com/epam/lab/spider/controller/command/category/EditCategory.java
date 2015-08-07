package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.ReplaceHtmlTags;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.persistence.service.CategoryService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzyuba Orest
 */
public class EditCategory implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("category");
        name = ReplaceHtmlTags.reaplaceAll(name);
        JSONObject json = new JSONObject();
        if (name.split("\\|").length != 2) {
            json.put("status", "warning");
            json.put("msg", "Неправильний формат категорії (name_ua|name_en)");
            response.getWriter().write(json.toString());
            return;
        }
        try {
            int id = Integer.parseInt(request.getParameter("catIdEdit"));
            Category category = service.getById(id);
            category.setName(name);
            String newImgUrl = (String) request.getSession().getAttribute("urlCat");
            if (newImgUrl != null) {
                category.setImageUrl(newImgUrl);
            }
            if (service.update(id, category)) {
                json.put("status", "success");
                json.put("msg", "Successfully updated!");
            } else {
                json.put("status", "error");
                json.put("msg", "Occured error while updated!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        response.getWriter().write(json.toString());
    }
}
