package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;

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
