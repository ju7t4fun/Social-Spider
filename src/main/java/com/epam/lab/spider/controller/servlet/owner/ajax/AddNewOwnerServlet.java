package com.epam.lab.spider.controller.servlet.owner.ajax;

import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Орест on 6/28/2015.
 */
public class AddNewOwnerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String publicUrl = request.getParameter("publicUrl");
        System.out.println("in servlet!!! and url is : " + publicUrl);

        JSONObject mainObj = new JSONObject();
        mainObj.put("SelectedProfilesRead","azaza");
        PrintWriter out = response.getWriter();
        out.print(mainObj);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
