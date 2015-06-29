package com.epam.lab.spider.controller.servlet.owner.ajax;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.Group;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Орест on 6/28/2015.
 */
public class AddNewOwnerServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String publicUrl = request.getParameter("publicUrl");
        System.out.println("in servlet!!! and url is : " + publicUrl);

        if (publicUrl != null) {

            String[] parts = publicUrl.split("/");

            String domain = parts[parts.length - 1];
            System.out.println(domain);

            Vkontakte vk = new Vkontakte();
            Parameters param = new Parameters();
            param.add("screen_name", domain);
            try {

                int id = vk.utils().resolveScreenName(param);
                System.out.println("id : " + id);


                param = new Parameters();
                param.add("group_id", id);
                List<Group> resList = vk.groups().getById(param);
                String nameGroup = resList.get(0).get(Group.NAME).toString();
                System.out.println(nameGroup);

                User user = (User) request.getSession().getAttribute("user");

                if (user != null) {
                    com.epam.lab.spider.model.db.entity.Owner owner = new com.epam.lab.spider.model.db.entity.Owner();
                    owner.setDeleted(false);
                    owner.setDomain(domain);
                    owner.setUserId(user.getId());
                    owner.setName(nameGroup);
                    owner.setVk_id(-1*id);
                    OwnerService ownerService = ServiceFactory.getInstance().create(OwnerService.class);
                    ownerService.insert(owner);
                }


            } catch (VKException e) {
                e.printStackTrace();
            }
        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("SelectedProfilesRead", "azaza");
        PrintWriter out = response.getWriter();
        out.print(mainObj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
