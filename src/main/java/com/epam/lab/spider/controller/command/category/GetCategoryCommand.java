package com.epam.lab.spider.controller.command.category;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.persistence.service.CategoryService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
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
 * @author Marian Voronovskyi
 */
public class GetCategoryCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GetCategoryCommand.class);
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static CategoryService service = factory.create(CategoryService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String toSearch = request.getParameter("sSearch");
        String pageNo = request.getParameter("iDisplayStart");
        String pageSize = request.getParameter("iDisplayLength");

        int start = 0;
        int amount = 5;

        if (pageNo!=null) {
            start = Integer.parseInt(pageNo);
            if (start < 0) {
                start = 0;
            }
        }

        if (pageSize!=null) {
            amount = Integer.parseInt(pageSize);
            if (amount < 5) {
                amount = 5;
            }
        }

        int totalCount;
        List<Category> categories;
        if (toSearch == null || toSearch.isEmpty()) {
            categories = service.getAllWithLimit(start,amount);
            totalCount = service.getCountAll();
        } else {
            toSearch = "%" + toSearch + "%";
            LOG.debug("toSearch: " + toSearch);
            categories = service.getAllWithSearchLimited(toSearch, start, amount);
            totalCount = service.getCountAllWithSearch(toSearch);
        }
        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();

        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int lang = Integer.parseInt(bundle.getString("categoryLangCode"));
        LOG.debug("code = " + lang);

        for (Category category : categories) {
            JSONArray row = new JSONArray();
            row.put(category.getId());
            row.put(category.getName().split("\\|")[lang] + "|" + category.getImageUrl());
            row.put(category.getName());
            table.put(row);
        }
        LOG.debug(categories.size());

        result.put("iTotalDisplayRecords", totalCount);
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }
}
