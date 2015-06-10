package com.epam.lab.spider.controller.vk;

import com.epam.lab.spider.controller.vk.auth.AccessToken;

public interface Authorization {

    AccessToken signIn(boolean needs);

}
