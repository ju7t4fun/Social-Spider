package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Authorization;
import com.epam.lab.spider.controller.vk.Scope;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class AddAccountCommand implements ActionCommand {

    private Vkontakte vk = null;

    public AddAccountCommand() {
        vk = new Vkontakte(4949213);
        vk.conf().setPermissions(Scope.WALL, Scope.GROUPS);
        vk.createOAuth(Authorization.Type.CLIENT);
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("user_id") == null) {
            vk.OAuth().open(response, false);
        } else {
            AccessToken token = vk.OAuth().signIn(request);
            HttpSession session = request.getSession();
            session.setAttribute("user_id", token.getUserId());
            session.setAttribute("access_token", token.getAccessToken());
            session.setAttribute("exp_moment", token.getExpirationMoment());
            response.sendRedirect("/user/accounts");
        }
    }

}
