package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import com.epam.lab.spider.model.vk.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 10.06.2015.
 */
public class VkAuthResponseCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService profileService = factory.create(ProfileService.class);
    private static UserService userService = factory.create(UserService.class);

    // Отримання даних поточного користувача з ВК
    private User authUser(Vkontakte vk) {
        User user = null;
        try {
            Parameters param = new Parameters();
            param.add("fields", "photo_200");
            List<User> users = vk.users().get(param);
            user = users.get(0);
        } catch (VKException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Vkontakte vk = (Vkontakte) session.getAttribute("siteVk");
        AccessToken token = vk.OAuth().signIn(request);
        vk.setAccessToken(token);
        User vkUser = authUser(vk);
        Profile vkProfile = profileService.getByVkId(vkUser.getId());
        if (vkProfile != null) {
            // Авторизація
            com.epam.lab.spider.model.db.entity.User user = userService.getById(vkProfile.getUserId());
            ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
            switch (user.getState()) {
                case CREATED:
                    // Користувач не активний
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.created")));
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case BANNED:
                    // Користувач забанений
                    request.setAttribute("toastr_notification", "warning|" + UTF8.encoding(bundle.getString("login" +
                            ".notification.banned")));
                    request.getRequestDispatcher("jsp/user/login.jsp").forward(request, response);
                    return;
                case ACTIVATED:
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("/");
            }
        } else {
            // Перехід на реєстрацію
            session.setAttribute("name", vkUser.getFirstName());
            session.setAttribute("surname", vkUser.getLastName());
            session.setAttribute("email", token.getEmail());
            session.setAttribute("vkId", token.getUserId());
            session.setAttribute("photo_200", vkUser.getPhoto200().toString());
            response.sendRedirect("/register");
        }
    }

}
