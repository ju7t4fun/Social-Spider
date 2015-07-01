package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.api.Utils;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.Group;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 01.07.2015.
 */
public class AddNewOwnewCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static OwnerService service = factory.create(OwnerService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        String ownerUrl = request.getParameter("ownerUrl");
        JSONObject json = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (ownerUrl != null) {
            String[] parts = ownerUrl.split("/");
            String domain = parts[parts.length - 1];
            String name = null;
            int id = 0;
            User user = (User) request.getSession().getAttribute("user");
            Vkontakte vk = new Vkontakte();
            Parameters param = new Parameters();
            param.add("screen_name", domain);
            try {
                Utils.ScreenName ownerScreenName = vk.utils().resolveScreenName(param);
                switch (ownerScreenName.getType()) {
                    case GROUP:
                    case PAGE:
                        param = new Parameters();
                        param.add("group_id", ownerScreenName.getObjectId());
                        name = vk.groups().getById(param).get(0).get(Group.NAME).toString();
                        id = -ownerScreenName.getObjectId();
                        break;
                    case USER:
                        param = new Parameters();
                        param.add("user_ids", ownerScreenName.getObjectId());
                        com.epam.lab.spider.model.vk.User user1 = vk.users().get(param).get(0);
                        name = user1.getFirstName() + " " + user1.getLastName();
                        id = ownerScreenName.getObjectId();
                        break;
                    case APPLICATION:
                        json.put("status", "error");
                        json.put("msg", "Не можливо додати application");
                        response.getWriter().write(json.toString());
                        return;
                }
            } catch (Exception e) {
                json.put("status", "error");
                json.put("msg", "Неможливо додати");
                response.getWriter().write(json.toString());
                return;
            }
            Owner owner = new Owner();
            owner.setVk_id(id);
            owner.setUserId(user.getId());
            owner.setName(name);
            owner.setDeleted(false);
            owner.setDomain(domain);
            if (service.insert(owner)) {
                json.put("status", "success");
                json.put("msg", UTF8.encoding(bundle.getString("notification.add.owner.success")));
            } else {
                json.put("status", "error");
                json.put("msg", UTF8.encoding(bundle.getString("notification.add.owner.error")));
            }
            response.getWriter().write(json.toString());
        }
    }

}
