package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class ShowAccountsCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Profile> profiles = service.getByUserId(1);
        String ids = null;
        for (Profile profile : profiles) {
            ids = ids == null ? "" + profile.getVkId() : ids + "," + profile.getVkId();
        }
        Vkontakte vk = new Vkontakte();
        Parameters param = new Parameters();
        param.add("user_ids", ids);
        List<User> users = null;
        try {
            users = vk.users().get(param);
        } catch (VKException e) {
            e.printStackTrace();
        }
        List<String> fullName = new ArrayList<>();
        for (User user : users) {
            fullName.add(user.getFirstName() + " " + user.getLastName());
        }
        request.setAttribute("profiles", profiles);
        request.setAttribute("fullNames", fullName);
        request.getRequestDispatcher("../jsp/user/user_accounts.jsp").forward(request, response);
    }
}
