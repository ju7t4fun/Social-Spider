package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

public class Auth extends Methods {

    public Auth(AccessToken token) {
        super(token);
    }

    /**
     * Проверяет правильность введённого номера.
     */
    public boolean checkPhone(Parameters param) throws VKException {
        Response response = request("auth.checkPhone", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Регистрирует нового пользователя по номеру телефона.
     */
    public String signup(Parameters param) throws VKException {
        Response response = request("auth.signup", param).execute();
        return response.root().child("sid").get(0).value().toString();
    }

    /**
     * Завершает регистрацию нового пользователя, начатую методом auth.signup, по коду, полученному через SMS.
     */
    public long confirm(Parameters param) throws VKException {
        Response response = request("auth.confirm", param).execute();
        return response.root().value().toLong();
    }

    /**
     * Позволяет восстановить доступ к аккаунту, используя код, полученный через СМС.
     */
    public String restore(Parameters param) throws VKException {
        Response response = request("auth.restore", param).execute();
        return response.root().child("sid").get(0).value().toString();
    }

}