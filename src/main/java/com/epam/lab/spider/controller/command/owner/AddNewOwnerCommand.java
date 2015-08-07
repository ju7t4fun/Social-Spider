package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.api.Utils;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.vk.Group;
import com.epam.lab.spider.persistence.service.OwnerService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class AddNewOwnerCommand implements ActionCommand {

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

            if (service.hasUserGroup(id, user.getId())) {
                json.put("status", "warning");
                json.put("msg", "Користувач має цю групу");
                response.getWriter().write(json.toString());
                return;
            }
            if (service.isBannedByVkId(id)) {
                json.put("status", "warning");
                json.put("msg", "Група забанена");
                response.getWriter().write(json.toString());
                return;
            }

            Owner owner = BasicEntityFactory.getSynchronized().createOwner();
            owner.setVkId(id);
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
