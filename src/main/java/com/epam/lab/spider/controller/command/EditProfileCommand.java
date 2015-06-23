package com.epam.lab.spider.controller.command;

import com.epam.lab.spider.controller.utils.FileType;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.db.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 21.06.2015.
 */
public class EditProfileCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String value = request.getParameter("value");

        response.setContentType("application/json");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("NAME: " + name);
        System.out.println("VALUE: " + value);
        if (name == null || name == "" || value == null || value == "") {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", "Field must not be " +
                    "empty!"));
        } else if (value.length() >= 25) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", "Field must be < 25 " +
                    "symbols"));
        } else if (value.matches("[a-zA-Z ]*\\d+.*")) {
            response.getWriter().print(new JSONObject().put("status", "error").put("msg", "Field must not contains " +
                    "digits"));
        } else {
            try {
                UserService userService = new UserService();
                userService.updateByParameter(name, value, user.getId());
                user = userService.getById(user.getId());
                session.setAttribute("user", user);
                response.getWriter().print(new JSONObject().put("fname", user.getName()).put("lname", user.getSurname()));
            } catch (Exception e) {
                e.printStackTrace();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "error");
                jsonObject.put("msg", "Error, please enter correct data!");
                response.getWriter().print(jsonObject.toString());
            }
        }


    }
}
