package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("locale/messages", locale);
        HttpSession session = request.getSession();
        session.setAttribute("bundle", bundle);
        Cookie cookie = new Cookie("language", lang);
        cookie.setMaxAge(24 * 60 * 60 * 60);


        String namesJson = request.getParameter("names");
        //request.
        System.out.println(namesJson);



        JSONObject resultJson = new JSONObject();

        try {
            JSONParser parser = new JSONParser();
            Object obj;
            obj = parser.parse(namesJson);
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i <jsonArray.length() ; i++) {
                String name = jsonArray.getString(i);
                String value = bundle.getString(name);
                resultJson.put(name,value);
            }
        } catch (ParseException|ClassCastException e) {
            e.printStackTrace();
        }



        response.addCookie(cookie);
        //response.sendRedirect("/");
        response.getOutputStream().print(resultJson.toString());
        response.flushBuffer();
    }

}
