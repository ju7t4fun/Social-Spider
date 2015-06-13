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

        boolean existsInBD = false;

        UserService serv = new UserService();
        User currUser = null;
        List<User> users = serv.getAll();
        if (users!=null) {
            for (int i = 0; i < users.size(); ++i) {
                if (users.get(i).getEmail().equalsIgnoreCase(email) && !users.get(i).getDeleted()) {
                    currUser = users.get(i);
                    existsInBD = true;
                }
            }
        }

        if (existsInBD) {
            request.getSession().setAttribute("registerMessage","User with email :" + currUser.getEmail()
                    + " already exists!");
            response.sendRedirect("/register");
            return;
        } else {

            DateFormat df = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            Date registrationDate = new Date();
            User user = new User();
            user.setConfirm(false);
            user.setDeleted(false);
            user.setEmail(email);
            /*String[] parts = username.split(" ");
            if (parts!=null && parts.length >0 ) {
                user.setSurname(parts[0]);
            }
            if (parts!=null && parts.length >1 ) {
                user.setName(parts[1]);
            }*/
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(new HashMD5().hash(password));
            //user.setCreateTime(df.format(registrationDate));
            user.setRole(User.Role.USER);
            System.out.println(serv.insert(user));

            HashSHA hashHelper = new HashSHA();
            String emailPartUri = "email="+email;
            String hashPartUri = "hash=" + hashHelper.hash(email + password);

            ConcreteSender.getInstance().send("http://localhost:8080/activation?action=activate&" +
                    emailPartUri+"&"+hashPartUri,email);

            request.getSession().setAttribute("activationMessage", "You have been successfuly registered. " +
                    "Please activate your account");
            response.sendRedirect("/activation");
            return;
        }
    }


}
