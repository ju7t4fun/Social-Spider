package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.dao.mysql.UserDAOImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class ActivateCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String hash = request.getParameter("hash");


        boolean userExistsInBD = false;


        if (userExistsInBD) {
            String password ="";

            //needtoDO: retrieve password from DB

            Date createDate = null; // from BD
            java.sql.Timestamp expireDate = new java.sql.Timestamp(createDate.getTime());

            Calendar c = Calendar.getInstance();
            c.setTime(expireDate);
            c.add(Calendar.DATE, 1);
            expireDate = new java.sql.Timestamp(c.getTime().getTime());
            java.sql.Timestamp currDate = new java.sql.Timestamp(createDate.getTime());
            if (hash.equals(code(password+email)) && currDate.before(expireDate) ) {

                //needtoDO: ACTIVATE ACC!!

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
                    " was deleted!");

            response.sendRedirect("/activation");
            return;
        }

    }
    private  String code(String someStr) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(someStr.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();

    }
}
