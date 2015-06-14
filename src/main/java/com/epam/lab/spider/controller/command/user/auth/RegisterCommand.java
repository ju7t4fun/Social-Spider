package com.epam.lab.spider.controller.command.user.auth;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.hash.HashMD5;
import com.epam.lab.spider.controller.hash.HashSHA;
import com.epam.lab.spider.controller.mail.ConcreteSender;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.service.UserService;
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
import java.util.List;

/**
 * Created by Dmytro on 11.06.2015.
 */
public class RegisterCommand implements ActionCommand {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userServ = new UserService();
        User currUser = userServ.getByEmail(email);

        if (currUser!=null && !currUser.getDeleted()) {
            request.getSession().setAttribute("registerMessage","User with email :" + currUser.getEmail()
                    + " already exists!");
            response.sendRedirect("/register");
            return;
        } else {

            User user = new User();
            user.setConfirm(false);
            user.setDeleted(false);
            user.setEmail(email);
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(new HashMD5().hash(password));
            user.setRole(User.Role.USER);
            userServ.insert(user);

            HashSHA hashHelper = new HashSHA();
            String emailPartUri = "email="+email;
            String hashPartUri = "hash=" + hashHelper.hash(email + user.getPassword());

            ConcreteSender.getInstance().send("http://localhost:8080/activation?action=activate&" +
                    emailPartUri+"&"+hashPartUri,email);

            request.getSession().setAttribute("activationMessage", "You have been successfuly registered. " +
                    "Please activate your account");
            response.sendRedirect("/activation");

            return;
        }
    }


}
