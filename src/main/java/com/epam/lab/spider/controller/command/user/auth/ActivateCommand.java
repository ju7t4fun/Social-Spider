package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.hash.HashSHA;
import com.epam.lab.spider.model.dao.mysql.UserDAOImp;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class ActivateCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String hash = request.getParameter("hash");

        UserService serv = new UserService();
        User currUser = null;
        List<User> users = serv.getAll();
        if (users != null) {
            for (int i = 0; i < users.size(); ++i) {
                if (users.get(i).getEmail().equalsIgnoreCase(email) && !users.get(i).getDeleted()) {
                    currUser = users.get(i);
                }
            }
        }

        if (currUser!=null) {

            Date createDate = currUser.getCreateTime();
            String password = currUser.getPassword();

            java.sql.Timestamp expireDate = new java.sql.Timestamp(createDate.getTime());

            Calendar c = Calendar.getInstance();
            c.setTime(expireDate);
            c.add(Calendar.DATE, 1);
            expireDate = new java.sql.Timestamp(c.getTime().getTime());
            java.sql.Timestamp currDate = new java.sql.Timestamp(createDate.getTime());
            HashSHA hashHelper = new HashSHA();
            System.out.println("Hash from uri: " + hash);
            System.out.println("hash from bd" + hashHelper.hash(email+password));
            System.out.println(currDate);
            System.out.println(expireDate);

            if (hash.equals(hashHelper.hash(email+password)) && currDate.before(expireDate) ) {

                currUser.setConfirm(true);
                serv.update(currUser.getId(),currUser);
                request.getSession().setAttribute("activationMessage","You have activated your account successfuly!");
                response.sendRedirect("/activation");
                return;

            } else {

                request.getSession().setAttribute("activationMessage","Bad or out of date uri!");
                response.sendRedirect("/activation");
                return;

            }
        } else {

            request.getSession().setAttribute("activationMessage","Sorry, user :" + email +
                    " doesn`t exist!");

            response.sendRedirect("/activation");
            return;
        }

    }

}
