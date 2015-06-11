package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Period;

import java.util.List;

public class Stats extends Methods {

    public Stats(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает статистику сообщества или приложения.
     */
    public List<Period> get(Parameters param) throws VKException {
        Response response = request("stats.get", param).execute();
        return Period.parsePeriod(response.root());
    }

    /**
     * Добавляет данные о текущем сеансе в статистику посещаемости приложения.
     */
    public boolean trackVisitor() throws VKException {
        Response response = request("stats.trackVisitor", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

}
