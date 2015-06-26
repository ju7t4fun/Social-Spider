package com.epam.lab.spider.controller.utils;

/**
 * Created by Boyarsky Vitaliy on 19.06.2015.
 */
public interface Sender {

    void accept(Receiver receiver);

    void history(int clientId);
}
