package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Authorization;
import com.epam.lab.spider.controller.vk.Scope;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 18.06.2015.
 */
public class RefreshAccountCommand implements ActionCommand {

    private static final Integer APP_ID = 4949213;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Vkontakte vk = new Vkontakte(APP_ID);
        vk.conf().setPermissions(Scope.WALL, Scope.GROUPS, Scope.PHOTOS, Scope.AUDIO, Scope.VIDEO, Scope.DOCS, Scope.STATS);
        vk.createOAuth(Authorization.Type.CLIENT);
        vk.OAuth().open(response, true);
    }

}
