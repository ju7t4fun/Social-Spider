package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Notification;

import java.util.List;

public class Notifications extends Methods {

    public Notifications(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список оповещений об ответах других пользователей на записи текущего пользователя.
     */
    public List<Notification> get(Parameters param) throws VKException {
        Response response = request("notifications.get", param).execute();
        return Notification.parseNotification(response.root());
    }

    /**
     * Сбрасывает счетчик непросмотренных оповещений об ответах других пользователей на записи текущего пользователя.
     */
    public boolean markAsViewed() throws VKException {
        Response response = request("notifications.markAsViewed", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

}
