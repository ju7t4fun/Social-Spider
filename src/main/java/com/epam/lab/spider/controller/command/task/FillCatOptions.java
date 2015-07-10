package com.epam.lab.spider.controller.command.task;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.service.CategoryService;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.TaskService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Орест on 7/10/2015.
 */
public class FillCatOptions implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService catService = factory.create(CategoryService.class);
    private static TaskService taskService = factory.create(TaskService.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            int taskId = Integer.parseInt(request.getParameter("id"));

        ResourceBundle bundle = (ResourceBundle) request.getSession().getAttribute("bundle");
        int lang = Integer.parseInt(bundle.getString("categoryLangCode"));

        List<Category> categories = catService.getAll();
        List<CategoryHasTask> chtS = taskService.getAllCHTByTaskId(taskId);
        JSONObject result = new JSONObject();
        result.put("categories", filtrationByExisting(categories,chtS, lang));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }


    private JSONArray filtrationByExisting(List<Category> categories,List<CategoryHasTask> chtS, int lang) {
        JSONArray array = new JSONArray();
        for (Category cat : categories) {
            JSONObject p = new JSONObject();
            p.put("name", cat.getName().split("\\|")[lang]);
            p.put("id", cat.getId());
            if (isInCHT(cat,chtS))
                p.put("select", true);
            else
                p.put("select", false);
            array.put(p);
        }
        return array;
    }

    private boolean isInCHT(Category cat, List<CategoryHasTask> chtS ) {

        for(CategoryHasTask cht : chtS ) {
            if (cht.getCategoryId() == cat.getId()) {
                return true;
            }
        }
        return false;
    }

}