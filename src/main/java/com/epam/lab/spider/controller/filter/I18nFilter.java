package com.epam.lab.spider.controller.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Boyarsky Vitaliy
 */
public class I18nFilter implements Filter {

    private static String defaultLanguage(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0)
            return "en";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("language")) {
                return cookie.getValue();
            }
        }
        return "en";
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        HttpSession session = ((HttpServletRequest) req).getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("locale/messages", new Locale("en", "US"));
            session.setAttribute("bundle", bundle);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
