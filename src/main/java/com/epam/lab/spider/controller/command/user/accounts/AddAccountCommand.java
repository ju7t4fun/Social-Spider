package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.ServerLocationUtils;
import com.epam.lab.spider.SocialNetworkCredentialsUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.*;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.vk.User;
import com.epam.lab.spider.persistence.service.ProfileService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class AddAccountCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(AddAccountCommand.class);

    private static final Integer APP_ID = SocialNetworkCredentialsUtils.getDefaultVkAppsIdAsApps();
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static ProfileService service = factory.create(ProfileService.class);

    private Vkontakte vk = null;

    public AddAccountCommand() {
        vk = new Vkontakte(APP_ID);
        vk.conf().setPermissions(Scope.WALL, Scope.GROUPS, Scope.PHOTOS, Scope.AUDIO, Scope.VIDEO, Scope.DOCS, Scope
                .STATS, Scope.OFFLINE);
        vk.createOAuth(Authorization.Type.CLIENT);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("user_id") == null) {
            // Відкриваєм вікно авторизації
            vk.OAuth().open(response, false);
        } else {
            // Користувач не підтвердив прав доступу
            if (request.getParameter("user_id").equals("undefined")) {
                response.sendRedirect(ServerLocationUtils.getServerPath(request)+"/accounts");
                return;
            }

            AccessToken token = vk.OAuth().signIn(request);
            Profile profile = BasicEntityFactory.getSynchronized().createProfile();
            profile.setVkId(token.getUserId());
            profile.setAccessToken(token.getAccessToken());
            profile.setExtTime(token.getExpirationMoment());
            profile.setUserId(((com.epam.lab.spider.model.entity.User) request.getSession().getAttribute("user"))
                    .getId());
            try {
                Parameters param = new Parameters();
                param.add("user_ids", token.getUserId());
                User user = vk.users().get(param).get(0);
                profile.setName(user.getFirstName() + " " + user.getLastName());
            } catch (VKException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
            profile.setAppId(APP_ID);

            // Перевіряємо чи такий профіль існує в базі
            Profile oldProfile = service.getByVkId(token.getUserId());
            if (oldProfile == null) {
                service.insert(profile);
            } else {
                service.update(oldProfile.getId(), profile);
            }
            response.sendRedirect(ServerLocationUtils.getServerPath(request)+"/accounts");
        }
    }

}
