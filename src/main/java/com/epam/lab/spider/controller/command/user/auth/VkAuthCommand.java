package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Authorization;
import com.epam.lab.spider.controller.vk.Scope;
import com.epam.lab.spider.controller.vk.Vkontakte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class VkAuthCommand implements ActionCommand {

    private Vkontakte vk = null;

    public VkAuthCommand() {
        vk = new Vkontakte(4949208);
        vk.conf().setPermissions(Scope.FRIENDS, Scope.EMAIL);
        vk.conf().setSecretKey("780FmVhvpLu8HobgGD8J");
        vk.createOAuth(Authorization.Type.SERVER);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("siteVk", vk);
        vk.OAuth().open(response, false);
    }

}
