package com.epam.lab.spider.controller.command.user.accounts;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class AddAccountCommand implements ActionCommand {

    private static String auth20 = "https://oauth.vk.com/authorize?client_id=4949213&scope=offline,docs,photos,video," +
            "audio,wall,friends,groups&redirect_uri=https://oauth.vk.com/blank.html&response_type=token";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(auth20);
    }
}
