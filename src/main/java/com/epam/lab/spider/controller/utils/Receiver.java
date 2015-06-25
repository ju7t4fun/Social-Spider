package com.epam.lab.spider.controller.utils;

/**
 * Created by Boyarsky Vitaliy on 19.06.2015.
 */
public interface Receiver {

    void visit(Sender sender);

    boolean send(int id, String message);

}
