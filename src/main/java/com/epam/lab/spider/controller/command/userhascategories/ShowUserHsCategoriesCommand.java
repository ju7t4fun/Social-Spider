package com.epam.lab.spider.controller.command.userhascategories;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.User;

import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Орест on 7/5/2015.
 */
public class ShowUserHsCategoriesCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService usServ = ServiceFactory.getInstance().create(CategoryService.class);

        int userId = ((User) request.getSession().getAttribute("user")).getId();

        List<Category> nonChosenList = usServ.getAllNonChosenCategories(userId);
        List<Category> chosenList = usServ.getAllChosenCategories(userId);

        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int lang = Integer.parseInt(bundle.getString("category"));

        chosenList = local(chosenList, lang);
        nonChosenList = local(nonChosenList, lang);

        request.setAttribute("chosen", chosenList);
        request.setAttribute("nonchosen", nonChosenList);

        request.getRequestDispatcher("jsp/user/choosecategories.jsp").forward(request, response);
    }

    private List<Category> local(List<Category> categories, int lang) {
        for (Category category : categories) {
            category.setName(category.getName().split("\\|")[lang]);
        }
        return categories;
    }

}
