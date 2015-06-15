package com.epam.lab.spider.controller.command.user.restore;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.utils.mail.MailSender;
import com.epam.lab.spider.controller.utils.mail.MailSenderFactory;
import com.epam.lab.spider.model.service.UserService;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;


/**
 * Created by Orest Dzyuba on 15.06.2015.
 */
public class SendRestoreCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");

        UserService userService = new UserService();
        if ( userService.getByEmail(email) != null ) {

            sendMail(request,email);
            request.setAttribute("restoreMessage","Restoration message was sent to your email!");

        } else {
            request.setAttribute("restoreMessage","There is no user with this email!");
        }

        request.getRequestDispatcher("/forgotpassword?action=default").forward(request, response);
        return;
    }

    private void sendMail(HttpServletRequest request, String email) {

        Date expDate = new Date(new Date().getTime() + 86400000);

        byte[] bytesEncoded = Base64.encodeBase64(Long.toString(expDate.getTime()).getBytes());
        String expDateEncoded = new String(bytesEncoded);

        bytesEncoded = Base64.encodeBase64(email.getBytes());
        String emailEncoded = new String(bytesEncoded);


        String timePart = "hash2=" + expDateEncoded;
        String emailPart = "hash1=" + emailEncoded;

        String restoreUrl = "http://localhost:8080/forgotpassword?action=retrieverestore" +
                "&" + timePart + "&" + emailPart;


        ResourceBundle bundle = (ResourceBundle) request.getSession().getAttribute("bundle");
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(UTF8.encoding(bundle.getString("reg.activate.welcome"))).append("<br>")
                .append("<a href=").append(restoreUrl).append(">").append(UTF8.encoding(bundle.getString("restore." +
                "clickRestoreMessage"))).append("</a>");

        MailSender mail = MailSenderFactory.createMailSender(MailSender.ContextType.HTML);
        mail.send(UTF8.encoding(bundle.getString("restore.restoreMessage")), html.toString(), email);

    }
}
