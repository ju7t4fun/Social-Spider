package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
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
 * Created by Boyarsky Vitaliy on 29.06.2015.
 */
public class GetOwnerCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static OwnerService service = factory.create(OwnerService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int page = Integer.parseInt(request.getParameter("iDisplayStart"));
        int size = Integer.parseInt(request.getParameter("iDisplayLength"));
        List<Owner> owners = null;
        int count = 0;
        if (request.getParameter("sSearch").length() > 0) {
            String q = request.getParameter("sSearch");
            owners = service.searchByUserId(user.getId(), q, page, size);
            count = service.getCountSearchByUserId(user.getId(), q);
        }else{
            owners = service.getByUserId(user.getId(), page, size);
            count = service.getCountByUserId(user.getId());
        }
        JSONObject result = new JSONObject();
        JSONArray table = new JSONArray();
        for (Owner owner : owners) {
            try {
                JSONArray row = new JSONArray();
                row.put(owner.getVkId());
                row.put(owner.getName());
                row.put(owner.getId());
                row.put(owner.getId());
                row.put(owner.getId());
                table.put(row);
            } catch (NullPointerException ignored) {
            }
        }
        result.put("iTotalDisplayRecords", count);
        result.put("aaData", table);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result.toString());
    }

}
