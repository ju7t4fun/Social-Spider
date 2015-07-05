package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Comment;
import com.epam.lab.spider.model.vk.Post;

import java.util.List;

public class Wall extends Methods {

    public Wall(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список записей со стены пользователя или сообщества.
     */
    public List<Post> get(Parameters param) throws VKException {
        Response response = request("wall.get", param).execute();
        return Post.parsePost(response.root().child("items").get(0));
    }
    /**
     * Возвращает список записей со стены пользователя или сообщества.
     */
    public Integer getCount(Integer ownerId) throws VKException {
        Parameters param = new Parameters();
        param.add("owner_id", ownerId);
        param.add("filter", "owner");
        param.add("count", 1);
        Response response = request("wall.get", param).execute();
//        Post.parsePost(response.root().child("items").get(0));
        return response.root().child("count").get(0).value().toInt();
    }

    /**
     * Метод, позволяющий осуществлять поиск по стенам пользователей.
     */
    public List<Post> search(Parameters param) throws VKException {
        Response response = request("wall.search", param).execute();
        return Post.parsePost(response.root());
    }

    /**
     * Возвращает список записей со стен пользователей или сообществ по их идентификаторам.
     */
    public List<Post> getById(Parameters param) throws VKException {
        Response response = request("wall.getById", param).execute();
        return Post.parsePost(response.root());
    }

    /**
     * Публикует новую запись на своей или чужой стене.
     */
    public long post(Parameters param) throws VKException {
        Response response = request("wall.post", param).execute();
        return response.root().child("post_id").get(0).value().toLong();
    }

    /**
     * Копирует объект на стену пользователя или сообщества.
     */
    public long repost(Parameters param) throws VKException {
        Response response = request("wall.repost", param).execute();
        return response.root().child("post_id").get(0).value().toLong();
    }

    /**
     * Позволяет получать список репостов заданной записи.
     */
    public List<Post> getReposts(Parameters param) throws VKException {
        Response response = request("wall.getReposts", param).execute();
        return Post.parsePost(response.root().child("items").get(0));
    }

    /**
     * Редактирует запись на стене.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("wall.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет запись со стены.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("wall.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленную запись на стене пользователя или сообщества.
     */
    public boolean restore(Parameters param) throws VKException {
        Response response = request("wall.restore", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Закрепляет запись на стене (запись будет отображаться выше остальных).
     */
    public boolean pin(Parameters param) throws VKException {
        Response response = request("wall.pin", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Отменяет закрепление записи на стене.
     */
    public boolean unpin(Parameters param) throws VKException {
        Response response = request("wall.unpin", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список комментариев к записи на стене.
     */
    public List<Comment> getComments(Parameters param) throws VKException {
        Response response = request("wall.getComments", param).execute();
        return Comment.parseComment(response.root());
    }

    /**
     * Добавляет комментарий к записи на стене пользователя или сообщества.
     */
    public int addComment(Parameters param) throws VKException {
        Response response = request("wall.addComment", param).execute();
        return response.root().child("comment_id").get(0).value().toInt();
    }

    /**
     * Редактирует комментарий на стене пользователя или сообщества.
     */
    public boolean editComment(Parameters param) throws VKException {
        Response response = request("wall.editComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет комментарий текущего пользователя к записи на своей или чужой стене.
     */
    public boolean deleteComment(Parameters param) throws VKException {
        Response response = request("wall.deleteComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает комментарий текущего пользователя к записи на своей или чужой стене.
     */
    public boolean restoreComment(Parameters param) throws VKException {
        Response response = request("wall.restoreComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет пожаловаться на запись.
     */
    public boolean reportPost(Parameters param) throws VKException {
        Response response = request("wall.reportPost", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Позволяет пожаловаться на комментарий к записи.
     */
    public boolean reportComment(Parameters param) throws VKException {
        Response response = request("wall.reportComment", param).execute();
        return response.root().value().toBoolean();
    }

}
