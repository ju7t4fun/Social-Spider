package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Group;
import com.epam.lab.spider.model.vk.User;

import java.util.List;

public class Users extends Methods {

    public Users(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает расширенную информацию о пользователях.
     */
    public List<User> get(Parameters param) throws VKException {
        Response response = request("users.get", param).execute();
        return User.parseUser(response.root());
    }

    /**
     * Возвращает список пользователей в соответствии с заданным критерием поиска.
     */
    public List<User> search(Parameters param) throws VKException {
        Response response = request("users.search", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Возвращает информацию о том, установил ли пользователь приложение.
     */
    public boolean isAppUser(Parameters param) throws VKException {
        Response response = request("users.isAppUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список идентификаторов пользователей и групп, которые входят в список подписок пользователя.
     */
    public Subscriptions getSubscriptions(final Parameters param) throws VKException {
        final Response response = request("users.getSubscriptions", param).execute();
        return new Subscriptions() {
            @Override
            public List<User> users() {
                Node root = response.root();
                if (root.hasChild("users"))
                    root = root.child("users").get(0);
                return User.parseUser(root.child("items").get(0));
            }

            @Override
            public List<Group> groups() {
                Node root = response.root();
                if (root.hasChild("groups"))
                    root = root.child("groups").get(0);
                return Group.parseGroup(root.child("items").get(0));
            }
        };
    }

    /**
     * Возвращает список идентификаторов пользователей, которые являются подписчиками пользователя.
     */
    public List<User> getFollowers(Parameters param) throws VKException {
        Response response = request("users.getFollowers", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Позволяет пожаловаться на пользователя.
     */
    public boolean report(Parameters param) throws VKException {
        Response response = request("users.report", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Индексирует текущее местоположение пользователя и возвращает список пользователей, которые находятся вблизи.
     */
    public List<User> getNearby(Parameters param) throws VKException {
        Response response = request("users.getNearby", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    public interface Subscriptions {
        List<User> users();

        List<Group> groups();
    }

}
