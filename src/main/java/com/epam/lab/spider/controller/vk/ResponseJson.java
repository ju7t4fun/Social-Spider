package com.epam.lab.spider.controller.vk;

import org.apache.http.HttpEntity;

public class ResponseJson implements Response {

    public ResponseJson(HttpEntity entity) {

    }

    @Override
    public Node root() {
        return null;
    }
}
