package com.epam.lab.spider.controller.vk;

import org.apache.http.HttpEntity;

public class ResponseFactory {

    public static Response getInstance(Response.Type type, HttpEntity entity) throws VKException {
        switch (type) {
            case XML:
                return new ResponseXml(entity);
            case JSON:
                return new ResponseJson(entity);
            default:
                return null;
        }
    }

}
