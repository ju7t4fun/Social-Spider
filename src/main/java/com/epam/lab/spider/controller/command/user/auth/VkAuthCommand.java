package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class VkAuthCommand implements ActionCommand {

    private static String auth = "https://oauth.vk" +
            ".com/authorize?client_id=4949208&scope=wall&redirect_uri=http://localhost:8080/login" +
            "&response_type=code&v=5.34&state=SESSION_STATE";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(auth);
    }

}
