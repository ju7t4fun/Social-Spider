package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 09.06.2015.
 */
public class LocaleCommand implements ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        if (lang.equals("undefined"))
            return;
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("locale/messages", locale);
        HttpSession session = request.getSession();
        session.setAttribute("bundle", bundle);
        Cookie cookie = new Cookie("language", lang);
        cookie.setMaxAge(24 * 60 * 60 * 60);

        String namesJson = request.getParameter("names");

        JSONObject resultJson = new JSONObject();

        try {
            JSONParser parser = new JSONParser();
            Object obj;
            obj = parser.parse(namesJson);
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                String name = (String) jsonArray.get(i);

                String value = UTF8.encoding(bundle.getString(name));
                resultJson.put(name, value);
            }
        } catch (ParseException | ClassCastException e) {
            e.printStackTrace();
        }

        String result = resultJson.toString();
        response.setContentType("application/json;charset=UTF-8");
        response.addCookie(cookie);
        //response.sendRedirect("/");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }

}
