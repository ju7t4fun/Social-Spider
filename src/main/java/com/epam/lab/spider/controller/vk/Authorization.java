package com.epam.lab.spider.controller.vk;

import com.epam.lab.spider.controller.vk.auth.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authorization {

    void open(HttpServletResponse response, boolean revoke);

    AccessToken signIn(HttpServletRequest request);

    enum Type {
        SERVER, CLIENT
    }

}
