package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.*;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.vk.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class AddAccountCommand implements ActionCommand {

    private static final Integer APP_ID = 4949213;
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    private Vkontakte vk = null;

    public AddAccountCommand() {
        vk = new Vkontakte(APP_ID);
        vk.conf().setPermissions(Scope.WALL, Scope.GROUPS, Scope.PHOTOS, Scope.AUDIO, Scope.VIDEO, Scope.DOCS, Scope
                .STATS);
        vk.createOAuth(Authorization.Type.CLIENT);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("user_id") == null) {
            // Відкриваєм вікно авторизації
            vk.OAuth().open(response, false);
        } else {
            // Користувач не підтвердив прав доступу
            if (request.getParameter("user_id").equals("undefined")) {
                response.sendRedirect("/accounts");
                return;
            }

            AccessToken token = vk.OAuth().signIn(request);
            Profile profile = new Profile();
            profile.setVkId(token.getUserId());
            profile.setAccessToken(token.getAccessToken());
            profile.setExtTime(token.getExpirationMoment());
            profile.setUserId(((com.epam.lab.spider.model.db.entity.User) request.getSession().getAttribute("user"))
                    .getId());
            try {
                Parameters param = new Parameters();
                param.add("user_ids", token.getUserId());
                User user = vk.users().get(param).get(0);
                profile.setName(user.getFirstName() + " " + user.getLastName());
            } catch (VKException e) {
                e.printStackTrace();
            }
            profile.setAppId(APP_ID);

            // Перевіряємо чи такий профіль існує в базі
            Profile oldProfile = service.getByVkId(token.getUserId());
            if (oldProfile == null) {
                service.insert(profile);
            } else {
                service.update(oldProfile.getId(), profile);
            }
            response.sendRedirect("/accounts");
        }
    }

}
