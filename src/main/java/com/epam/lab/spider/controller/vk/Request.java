package com.epam.lab.spider.controller.vk;

import java.util.Locale;

public interface Request {

    String HTTP_HOST_METHOD = "https://api.vk.com/method/";

    Response execute() throws VKException;

    public enum Method {
        GET, POST;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.US);
        }

    }

}
