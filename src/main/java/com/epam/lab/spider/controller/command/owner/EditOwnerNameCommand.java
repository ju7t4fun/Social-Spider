package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marian Voronovskyi on 01.07.2015.
 */
public class EditOwnerNameCommand implements ActionCommand {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static OwnerService service = factory.create(OwnerService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ownerId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        String name = request.getParameter("name");
        JSONObject result = new JSONObject();
        try {
            Owner owner = service.getById(ownerId);
            owner.setName(name);
            owner.setUserId(user.getId());
            System.out.println(owner);
            service.update(ownerId, owner);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("msg", "");
            response.getWriter().write(result.toString());
            return;
        }
    }
}
