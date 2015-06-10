package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

public class Status extends Methods {

    public Status(AccessToken token) {
        super(token);
    }

    /**
     * Устанавливает новый статус текущему пользователю или сообществу.
     */
    public boolean set(Parameters param) throws VKException {
        Response response = request("status.set", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Получает текст статуса пользователя или сообщества.
     */
    public String get(Parameters param) throws VKException {
        Response response = request("status.get", param).execute();
        return response.root().child("text").get(0).value().toString();
    }

}
