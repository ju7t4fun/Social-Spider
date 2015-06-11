package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Link;

import java.util.Date;

public class Utils extends Methods {

    public Utils(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает информацию о том, является ли внешняя ссылка заблокированной на сайте ВКонтакте.
     */
    public Link.Status checkLink(Parameters param) throws VKException {
        Response response = request("utils.checkLink", param).execute();
        return Link.Status.getByName(response.root().child("status").get(0).value().toString());
    }

    /**
     * Определяет тип объекта (пользователь, сообщество, приложение) и его идентификатор по короткому имени screen_name.
     */
    public int resolveScreenName(Parameters param) throws VKException {
        final Response response = request("utils.resolveScreenName", param).execute();
        return response.root().child("object_id").get(0).value().toInt();
    }

    /**
     * Возвращает текущее время на сервере ВКонтакте.
     */
    public Date getServerTime() throws VKException {
        Response response = request("utils.getServerTime", new Parameters()).execute();
        return response.root().value().toDate();
    }

}
