package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.facebookauth.FBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dzyuba Orest
 */
public class FbAuthCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(new FBConnection().getFBAuthUrl());
    }
}
