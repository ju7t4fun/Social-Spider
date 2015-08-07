package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.Authorization;
import com.epam.lab.spider.integration.vk.Scope;
import com.epam.lab.spider.integration.vk.Vkontakte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class RefreshAccountCommand implements ActionCommand {

    private static final Integer APP_ID = SocialNetworkUtils.getDefaultVkAppsIdAsApps();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Vkontakte vk = new Vkontakte(APP_ID);
        vk.conf().setPermissions(Scope.WALL, Scope.GROUPS, Scope.PHOTOS, Scope.AUDIO, Scope.VIDEO, Scope.DOCS, Scope.STATS);
        vk.createOAuth(Authorization.Type.CLIENT);
        vk.OAuth().open(response, true);
    }

}
