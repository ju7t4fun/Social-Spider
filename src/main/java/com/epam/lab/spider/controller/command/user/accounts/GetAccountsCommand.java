package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ProfileService;
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
 * Created by Boyarsky Vitaliy on 06.07.2015.
 */
public class GetAccountsCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int page = Integer.parseInt(request.getParameter("iDisplayStart"));
        int size = Integer.parseInt(request.getParameter("iDisplayLength"));
        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();
        List<Profile> profiles = service.getByUserId(user.getId(), page, size);
        for (Profile profile : profiles) {
            JSONArray row = new JSONArray();
            row.put(profile.getVkId());
            row.put(profile.getName());
            row.put("http://vk.com/id" + profile.getVkId());
            row.put(profile.getId());
            table.put(row);
        }
        int count = service.getCountByUserId(user.getId());
        result.put("iTotalDisplayRecords", count);
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }

}
