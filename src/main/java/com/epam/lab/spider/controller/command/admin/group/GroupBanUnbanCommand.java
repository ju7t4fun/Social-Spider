package com.epam.lab.spider.controller.command.admin.group;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.persistence.service.OwnerService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Dzyuba Orest
 */
public class GroupBanUnbanCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(GroupBanUnbanCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        String vkIdString = request.getParameter("vk_id");

        int vkId = Integer.parseInt(vkIdString);

        OwnerService service = ServiceFactory.getInstance().create(OwnerService.class);
        JSONObject json = new JSONObject();
        try {
            Owner owner = service.getByVkId(vkId);
            if (owner == null || owner.getBanned()) {
                if (service.updateUnBan(vkId)) {
                    json.put("status", "success");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.updated.unbanned.success")));
                } else {
                    json.put("status", "error");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.updated.unbanned.error")));
                }
            } else {
                if (service.updateBan(vkId)) {
                    json.put("status", "success");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.updated.banned.success")));
                } else {
                    json.put("status", "error");
                    json.put("msg", UTF8.encoding(bundle.getString("notification.updated.banned.error")));
                }
                service.deleteByVkId(vkId);
            }


        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
