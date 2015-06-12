package com.epam.lab.spider.controller.mail;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class ConcreteSender {

    Sender sender;

    private static ConcreteSender instance = null;

    private ConcreteSender() {
        sender = new Sender("epam.social.spider@gmail.com","epamspider");
    }

    public static ConcreteSender getInstance() {
        if (instance == null) {
            instance = new ConcreteSender();
        }
        return  instance;
    }

    public void send(String message, String destinationMail) {
        sender.send("Activation mail", message, destinationMail );
        System.out.println("done!");
    }
}
