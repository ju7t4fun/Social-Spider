package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.SocialNetworkCredentialsUtils;
import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.integration.vk.Authorization;
import com.epam.lab.spider.integration.vk.Scope;
import com.epam.lab.spider.integration.vk.Vkontakte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Boyarsky Vitaliy
 */
public class VkAuthCommand implements ActionCommand {

    private Vkontakte vk = null;

    public VkAuthCommand() {
        vk = new Vkontakte(SocialNetworkCredentialsUtils.getDefaultVkAppsIdAsSite());
        vk.conf().setPermissions(Scope.FRIENDS, Scope.EMAIL);
        vk.conf().setSecretKey(SocialNetworkCredentialsUtils.getDefaultVkSecretCodeAsSite());
        vk.createOAuth(Authorization.Type.SERVER);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("siteVk", vk);
        vk.OAuth().open(response, true);
    }

}
