package com.epam.lab.spider.controller.utils;

/**
 * @author Boyarsky Vitaliy
 */
public interface Sender {

    void accept(Receiver receiver);

    void history(int clientId);
}
