package com.epam.lab.spider.controller.utils;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Created by Marian Voronovskyi on 11.06.2015.
 */
public class SendEmail {
    public static void send(String to, String title, String html) {
        final String username = "epam.social.spider@gmail.com";
        final String password = "epamspider";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("epam.social.spider@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(title);
            message.setContent(html, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
