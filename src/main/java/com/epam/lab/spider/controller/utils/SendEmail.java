package com.epam.lab.spider.controller.utils;


import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Marian Voronovskyi
 */
public class SendEmail {
    private static final Logger LOG = Logger.getLogger(SendEmail.class);
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
        } catch (MessagingException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

}
