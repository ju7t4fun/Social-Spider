package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Comment;
import com.epam.lab.spider.model.vk.Topic;

import java.util.List;

public class Board extends Methods {

    public Board(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список тем в обсуждениях указанной группы.
     */
    public List<Topic> getTopics(Parameters param) throws VKException {
        Response response = request("board.getTopics", param).execute();
        return Topic.parseTopic(response.root().child("items").get(0));
    }

    /**
     * Возвращает список сообщений в указанной теме.
     */
    public List<Comment> getComments(Parameters param) throws VKException {
        Response response = request("board.getComments", param).execute();
        return Comment.parseComment(response.root().child("items").get(0));
    }

    /**
     * Создает новую тему в списке обсуждений группы.
     */
    public int addTopic(Parameters param) throws VKException {
        Response response = request("board.addTopic", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Добавляет новое сообщение в теме сообщества.
     */
    public int addComment(Parameters param) throws VKException {
        Response response = request("board.addComment", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Удаляет тему в обсуждениях группы.
     */
    public boolean deleteTopic(Parameters param) throws VKException {
        Response response = request("board.deleteTopic", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Изменяет заголовок темы в списке обсуждений группы.
     */
    public boolean editTopic(Parameters param) throws VKException {
        Response response = request("board.editTopic", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Редактирует одно из сообщений в теме группы.
     */
    public boolean editComment(Parameters param) throws VKException {
        Response response = request("board.editComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удаленное сообщение темы в обсуждениях группы.
     */
    public boolean restoreComment(Parameters param) throws VKException {
        Response response = request("board.restoreComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет сообщение темы в обсуждениях сообщества.
     */
    public boolean deleteComment(Parameters param) throws VKException {
        Response response = request("board.deleteComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Открывает ранее закрытую тему (в ней станет возможно оставлять новые сообщения).
     */
    public boolean openTopic(Parameters param) throws VKException {
        Response response = request("board.openTopic", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Закрывает тему в списке обсуждений группы (в такой теме невозможно оставлять новые сообщения).
     */
    public boolean closeTopic(Parameters param) throws VKException {
        Response response = request("board.closeTopic", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Закрепляет тему в списке обсуждений группы (такая тема при любой сортировке выводится выше остальных).
     */
    public boolean fixTopic(Parameters param) throws VKException {
        Response response = request("board.fixTopic", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Отменяет прикрепление темы в списке обсуждений группы (тема будет выводиться согласно выбранной сортировке).
     */
    public boolean unfixTopic(Parameters param) throws VKException {
        Response response = request("board.unfixTopic", param).execute();
        return response.root().value().toBoolean();
    }

}
