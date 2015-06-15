package com.epam.lab.spider.controller.utils.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Dmytro on 12.06.2015.
 */

public class MailSender {

    private final String username;
    private final String password;
    private final Properties props;
    private final ContextType contextType;

    public MailSender(String username, String password) {
        this(username, password, ContextType.TEXT);
    }

    public MailSender(String username, String password, ContextType contextType) {
        this.username = username;
        this.password = password;
        this.contextType = contextType;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public void send(String subject, String text, String toEmail) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            switch (contextType) {
                case HTML:
                    message.setContent(text, "text/html; charset=utf-8");
                    break;
                case TEXT:
                    message.setText(text);
            }
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public enum ContextType {
        TEXT, HTML
    }

}