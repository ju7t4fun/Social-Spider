package com.epam.lab.spider.controller.command.admin;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Орест on 7/2/2015.
 */
public class GroupBanUnbanCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vkid = request.getParameter("vk_id");

        System.out.println("in ban-unban servlet!!!  vkid: " + vkid);

        int vk_id = Integer.parseInt(vkid);

        OwnerService service = ServiceFactory.getInstance().create(OwnerService.class);
        JSONObject json = new JSONObject();
        try {
            if ( service.getByVkId(vk_id).getBanned() ) {
                System.out.println("State : " + service.getByVkId(vk_id).getBanned());
                if ( service.updateUnBan(vk_id) ) {
                    System.out.println("State : " + service.getByVkId(vk_id).getBanned());
                    json.put("status", "success");
                    json.put("msg", "Updated : unbanned");
                }
                else {
                    json.put("status", "error");
                    json.put("msg", "Error");
                }
            } else {
                System.out.println("State : " + service.getByVkId(vk_id).getBanned());
               if  (service.updateBan(vk_id)) {
                   System.out.println("State : " + service.getByVkId(vk_id).getBanned());
                   json.put("status", "success");
                   json.put("msg", "Updated : banned");
               } else {
                   json.put("status", "error");
                   json.put("msg", "Error");
               }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
    }
}
