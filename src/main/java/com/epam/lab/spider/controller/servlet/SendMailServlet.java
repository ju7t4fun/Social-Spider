package com.epam.lab.spider.controller.servlet;

import com.epam.lab.spider.controller.utils.SendEmail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class SendMailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SendEmail.send("marik1993@i.ua", "title", "<p><a href=\"http://google.com\">Google</a></p>");
        request.getRequestDispatcher("/").forward(request,
                response);
    }
}
