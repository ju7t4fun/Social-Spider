package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.*;

import java.util.List;

public class Fave extends Methods {

    public Fave(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список пользователей, добавленных текущим пользователем в закладки.
     */
    public List<User> getUsers(Parameters param) throws VKException {
        Response response = request("fave.getUsers", param).execute();
        return User.parseUser(response.root().child("items").get(0));
    }

    /**
     * Возвращает фотографии, на которых текущий пользователь поставил отметку "Мне нравится".
     */
    public List<Photo> getPhotos(Parameters param) throws VKException {
        Response response = request("fave.getPhotos", param).execute();
        return Photo.parsePhoto(response.root().child("items").get(0));
    }

    /**
     * Возвращает записи, на которых текущий пользователь поставил отметку «Мне нравится».
     */
    public List<Post> getPosts(Parameters param) throws VKException {
        Response response = request("fave.getPosts", param).execute();
        return Post.parsePost(response.root().child("items").get(0));
    }

    /**
     * Возвращает список видеозаписей, на которых текущий пользователь поставил отметку «Мне нравится».
     */
    public List<Video> getVideos(Parameters param) throws VKException {
        Response response = request("fave.getVideos", param).execute();
        return Video.parseVideo(response.root().child("items").get(0));
    }

    /**
     * Возвращает ссылки, добавленные в закладки текущим пользователем.
     */
    public List<Link> getLinks(Parameters param) throws VKException {
        Response response = request("fave.getLinks", param).execute();
        return Link.parseLink(response.root().child("items").get(0));
    }

    /**
     * Добавляет пользователя в закладки.
     */
    public boolean addUser(Parameters param) throws VKException {
        Response response = request("fave.addUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет пользователя из закладок.
     */
    public boolean removeUser(Parameters param) throws VKException {
        Response response = request("fave.removeUser", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Добавляет сообщество в закладки.
     */
    public boolean addGroup(Parameters param) throws VKException {
        Response response = request("fave.addGroup", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет сообщество из закладок.
     */
    public boolean removeGroup(Parameters param) throws VKException {
        Response response = request("fave.removeGroup", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Добавляет ссылку в закладки.
     */
    public boolean addLink(Parameters param) throws VKException {
        Response response = request("fave.addLink", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет ссылку из закладок.
     */
    public boolean removeLink(Parameters param) throws VKException {
        Response response = request("fave.removeLink", param).execute();
        return response.root().value().toBoolean();
    }

}