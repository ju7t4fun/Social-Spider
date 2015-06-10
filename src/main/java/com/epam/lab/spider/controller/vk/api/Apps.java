package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.App;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Apps extends Methods {

    public Apps(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список приложений, доступных для пользователей сайта через каталог приложений.
     */
    public List<App> getCatalog(Parameters param) throws VKException {
        Response response = request("apps.getCatalog", param).execute();
        return App.parseApp(response.root().child("items").get(0));
    }

    /**
     * Возвращает данные о запрошенном приложении на платформе ВКонтакте
     */
    public App get(Parameters param) throws VKException {
        Response response = request("apps.get", param).execute();
        return new App(response.root());
    }

    /**
     * Позволяет отправить запрос другому пользователю в приложении, использующем авторизацию ВКонтакте.
     */
    public int sendRequest(Parameters param) throws VKException {
        Response response = request("apps.sendRequest", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет все уведомления о запросах, отправленных из текущего приложения
     */
    public boolean deleteAppRequests(Parameters param) throws VKException {
        Response response = request("apps.deleteAppRequests", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Создает список друзей, который будет использоваться при отправке пользователем приглашений в приложение.
     */
    public List<User> getFriendsList(Parameters param) throws VKException {
        param.add("extended", 1);
        Response response = request("apps.getFriendsList", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

}
