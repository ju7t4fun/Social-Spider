package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.FriendStatus;
import com.epam.lab.spider.model.vk.FriendsList;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Friends extends Methods {

    public Friends(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список идентификаторов друзей пользователя или расширенную информацию о друзьях пользователя.
     */
    public List<User> get(Parameters param) throws VKException {
        Response response = request("friends.get", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Возвращает список идентификаторов друзей пользователя, находящихся на сайте.
     */
    public List<User> getOnline(Parameters param) throws VKException {
        Response response = request("friends.getOnline", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает список идентификаторов общих друзей между парой пользователей.
     */
    public List<User> getMutual(Parameters param) throws VKException {
        Response response = request("friends.getMutual", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает список идентификаторов недавно добавленных друзей текущего пользователя
     */
    public List<User> getRecent(Parameters param) throws VKException {
        Response response = request("friends.getRecent", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает информацию о полученных или отправленных заявках на добавление в друзья для текущего пользователя.
     */
    public List<User> getRequests(Parameters param) throws VKException {
        Response response = request("friends.getRequests", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Одобряет или создает заявку на добавление в друзья.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("friends.add", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Редактирует списки друзей для выбранного друга.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("friends.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет пользователя из списка друзей или отклоняет заявку в друзья.
     */
    public int delete(Parameters param) throws VKException {
        Response response = request("friends.delete", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает список меток друзей текущего пользователя.
     */
    public List<FriendsList> getLists(Parameters param) throws VKException {
        Response response = request("friends.getLists", param).execute();
        return FriendsList.parseFriendsList(response.root().child("items").get(0));
    }

    /**
     * Создает новый список друзей у текущего пользователя.
     */
    public int addList(Parameters param) throws VKException {
        Response response = request("friends.addList", param).execute();
        return response.root().child("lid").get(0).value().toInt();
    }

    /**
     * Редактирует существующий список друзей текущего пользователя.
     */
    public boolean editList(Parameters param) throws VKException {
        Response response = request("friends.editList", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет существующий список друзей текущего пользователя.
     */
    public boolean deleteList(Parameters param) throws VKException {
        Response response = request("friends.deleteList", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список идентификаторов друзей текущего пользователя, которые установили данное приложение.
     */
    public List<User> getAppUsers() throws VKException {
        Response response = request("friends.getAppUsers", new Parameters()).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает список друзей пользователя, у которых завалидированные или указанные в профиле телефонные номера
     * входят в заданный список.
     */
    public List<User> getByPhones(Parameters param) throws VKException {
        Response response = request("friends.getByPhones", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Отмечает все входящие заявки на добавление в друзья как просмотренные.
     */
    public boolean deleteAllRequests() throws VKException {
        Response response = request("friends.deleteAllRequests", new Parameters()).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список профилей пользователей, которые могут быть друзьями текущего пользователя.
     */
    public List<User> getSuggestions(Parameters param) throws VKException {
        Response response = request("friends.getSuggestions", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает информацию о том, добавлен ли текущий пользователь в друзья у указанных пользователей.
     */
    public List<FriendStatus> areFriends(Parameters param) throws VKException {
        Response response = request("friends.areFriends", param).execute();
        return FriendStatus.parseFriendStatus(response.root());
    }

    /**
     * Позволяет получить список идентификаторов пользователей доступных для вызова в приложении используя метод
     * JSAPI callUser.
     */
    public List<User> getAvailableForCall(Parameters param) throws VKException {
        Response response = request("friends.getAvailableForCall", param).execute();
        return User.parseUser(response.root());
    }

}
