package com.epam.lab.spider.controller.utils;

/**
 * @author Boyarsky Vitaliy
 */
public interface Receiver {

    void visit(Sender sender);

    boolean send(int id, String message);

}
