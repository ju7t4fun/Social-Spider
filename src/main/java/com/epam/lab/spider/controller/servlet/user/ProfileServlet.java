package com.epam.lab.spider.controller.servlet.user;

import com.epam.lab.spider.controller.command.ActionFactory;
import com.epam.lab.spider.controller.command.user.profile.ChangeAvatarCommand;
import com.epam.lab.spider.controller.command.user.profile.ProfileChangePasswordCommand;
import com.epam.lab.spider.controller.command.user.profile.EditProfileCommand;
import com.epam.lab.spider.controller.command.user.profile.ShowProfileCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Boyarsky Vitaliy on 26.06.2015.
 */
public class ProfileServlet extends HttpServlet {

    private static ActionFactory factory = new ProfileActionFactory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        factory.action(request, response);
    }

    private static class ProfileActionFactory extends ActionFactory {

        public ProfileActionFactory() {
            commands = new HashMap<>();
            commands.put("default", new ShowProfileCommand());
            commands.put("changeAvatar", new ChangeAvatarCommand());
            commands.put("edit", new EditProfileCommand());
            commands.put("change", new ProfileChangePasswordCommand());
        }

    }
}
