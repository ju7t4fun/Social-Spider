package com.epam.lab.spider;

import com.epam.lab.spider.controller.vk.Scope;
import com.epam.lab.spider.controller.vk.Vkontakte;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) {
        Vkontakte vk = new Vkontakte(4949208);
        vk.conf().setPermissions(Scope.FRIENDS, Scope.WALL, Scope.GROUPS);
        vk.conf().setSecretKey("780FmVhvpLu8HobgGD8J");

    }

}
