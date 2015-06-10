package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Request;
import com.epam.lab.spider.controller.vk.RequestFactory;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import org.apache.log4j.Logger;

public abstract class Methods {

    private static final Logger logger = Logger.getLogger(Methods.class);

    private static final String API_VERSION = "5.27";
    private AccessToken token;

    public Methods(AccessToken token) {
        this.token = token;
    }

    protected Request request(String name, Parameters param) {
        param.add("v", API_VERSION);
        if (token != null) {
            param.add("access_token", token.getAccessToken());
        }
        logger.info("Выполнение запроса \"" + name + "\" к API Вконтакте");
        return RequestFactory.getRequest(name, param);
    }

}