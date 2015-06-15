package com.epam.lab.spider.controller.utils.mail;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class MailSenderFactory {

    private static final String EMAIL = "epam.social.spider@gmail.com";
    private static final String PASSWORD = "epamspider";

    private static MailSender htmlMailSender = new MailSender(EMAIL, PASSWORD, MailSender.ContextType.HTML);
    private static MailSender mailSender = new MailSender(EMAIL, PASSWORD);

    private MailSenderFactory() {
        super();
    }

    public static MailSender createMailSender(MailSender.ContextType contextType) {
        switch (contextType) {
            case TEXT:
                return mailSender;
            case HTML:
                return htmlMailSender;
        }
        throw new NullPointerException();
    }

}
