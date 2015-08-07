package com.epam.lab.spider.controller.command.controller;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import org.apache.log4j.Logger;
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
 * @author Boyarsky Vitaliy
 * @author Yura Kovalik
 * @since 1.0.2
 */
public class LocaleCommand implements ActionCommand {
    private static final Logger LOG = Logger.getLogger(LocaleCommand.class);
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
            ResourceBundle bundle;
            HttpSession session = request.getSession();
            Cookie cookie = null;
        if (lang!= null && !lang.equals("undefined")) {
            Locale locale = new Locale(lang);
            bundle = ResourceBundle.getBundle("locale/messages", locale);
            session.setAttribute("bundle", bundle);
            cookie = new Cookie("language", lang);
            cookie.setMaxAge(24 * 60 * 60 * 60);
        }else{
            bundle = (ResourceBundle) session.getAttribute("bundle");
        }
        String namesJson = request.getParameter("names");

        JSONObject resultJson = new JSONObject();

        try {
            JSONParser parser = new JSONParser();
            Object obj;
            obj = parser.parse(namesJson);
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                    String name = null;
                try {
                    name = (String) jsonArray.get(i);
                    String value = UTF8.encoding(bundle.getString(name));
                    resultJson.put(name, value);
                }catch (java.util.MissingResourceException x){
                    LOG.error("MissingResource:"+name);
                }catch (RuntimeException x){
                    LOG.error(x);
                }
            }
        } catch (ParseException | ClassCastException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        String result = resultJson.toString();
        response.setContentType("application/json;charset=UTF-8");
        if(cookie!=null)response.addCookie(cookie);
        //response.sendRedirect(ServerResolver.getServerPath(request)+"/");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }

}

