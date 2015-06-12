package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.mail.ConcreteSender;
import org.apache.http.client.utils.URIBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmytro on 11.06.2015.
 */
public class RegisterCommand implements ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean existsInBD = false;

        if (existsInBD) {
            response.sendRedirect("/register");
            return;
        } else {

            //needtodo: add registrationDate while adding user to DB!!!!
            Date registrationDate = new Date();
            //needtoDo: add new user with non-activated status

            //sending email


            String emailPartUri = "email="+email;
            String hashPartUri = "hash=" + code(email+password);

            ConcreteSender.getInstance().send("http://localhost:8080/activation?action=activate&" +
                    emailPartUri+"&"+hashPartUri,"dzyubaorest@gmail.com");

            request.getSession().setAttribute("activationMessage","You have been successfuly registered. " +
                    "Please activate your account");
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
