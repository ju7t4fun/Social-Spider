package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class LocaleCommand implements ActionCommand {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("locale/messages", locale);
        HttpSession session = request.getSession();
        session.setAttribute("bundle", bundle);
        Cookie cookie = new Cookie("language", lang);
        cookie.setMaxAge(24 * 60 * 60 * 60);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

}
