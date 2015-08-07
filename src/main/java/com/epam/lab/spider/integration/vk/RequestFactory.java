package com.epam.lab.spider.integration.vk;

public class RequestFactory {

    public static Request getRequest(String method, Parameters param) {
        switch (param.getRequestMethod()) {
            case GET:
                return new RequestGet(method, param);
            case POST:
                return new RequestPost(method, param);
            default:
                return null;
        }
    }

}
