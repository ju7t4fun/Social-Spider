package com.epam.lab.spider.controller.websocket;

/**
 * Created by Boyarsky Vitaliy on 19.06.2015.
 */
public interface Receiver {

    void visit(Sender sender);

    void send(int id, String message);

}
