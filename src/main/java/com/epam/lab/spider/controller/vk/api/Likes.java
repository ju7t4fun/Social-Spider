package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Likes extends Methods {

    public Likes(AccessToken token) {
        super(token);
    }

    /**
     * Получает список идентификаторов пользователей, которые добавили заданный объект в свой список Мне нравится.
     */
    public List<User> getList(Parameters param) throws VKException {
        param.add("extended", 1);
        Response response = request("likes.getList", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Добавляет указанный объект в список Мне нравится текущего пользователя.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("likes.add", param).execute();
        return response.root().child("likes").get(0).value().toInt();
    }

    /**
     * Удаляет указанный объект из списка Мне нравится текущего пользователя.
     */
    public int delete(Parameters param) throws VKException {
        Response response = request("likes.delete", param).execute();
        return response.root().child("likes").get(0).value().toInt();
    }

    /**
     * Проверяет, находится ли объект в списке Мне нравится заданного пользователя.
     */
    public boolean isLiked(Parameters param) throws VKException {
        Response response = request("likes.isLiked", param).execute();
        return response.root().value().toBoolean();
    }

}
