package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Gift;

import java.util.List;

public class Gifts extends Methods {

    public Gifts(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список полученных подарков пользователя.
     */
    public List<Gift> get(Parameters param) throws VKException {
        Response response = request("gifts.get", param).execute();
        return Gift.parseGift(response.root().child("items").get(0));
    }

}