package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Note;

import java.util.List;

public class Notes extends Methods {

    public Notes(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает список заметок, созданных пользователем.
     */
    public List<Note> get(Parameters param) throws VKException {
        Response response = request("notes.get", param).execute();
        return Note.parseNote(response.root().child("items").get(0));
    }

    /**
     * Возвращает заметку по её id.
     */
    public List<Note> getById(Parameters param) throws VKException {
        Response response = request("notes.getById", param).execute();
        return Note.parseNote(response.root());
    }

    /**
     * Возвращает список заметок друзей пользователя.
     */
    public List<Note> getFriendsNotes(Parameters param) throws VKException {
        Response response = request("notes.getFriendsNotes", param).execute();
        return Note.parseNote(response.root().child("items").get(0));
    }

    /**
     * Создает новую заметку у текущего пользователя.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("notes.add", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Редактирует заметку текущего пользователя.
     */
    public boolean edit(Parameters param) throws VKException {
        Response response = request("notes.edit", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет заметку текущего пользователя.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("notes.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает список комментариев к заметке.
     */
    public List<Note.Comment> getComments(Parameters param) throws VKException {
        Response response = request("notes.getComments", param).execute();
        return Note.Comment.parseComment(response.root().child("items").get(0));
    }

    /**
     * Добавляет новый комментарий к заметке.
     */
    public boolean createComment(Parameters param) throws VKException {
        Response response = request("notes.createComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Редактирует указанный комментарий у заметки.
     */
    public boolean editComment(Parameters param) throws VKException {
        Response response = request("notes.editComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет комментарий к заметке.
     */
    public boolean deleteComment(Parameters param) throws VKException {
        Response response = request("notes.deleteComment", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Восстанавливает удалённый комментарий.
     */
    public boolean restoreComment(Parameters param) throws VKException {
        Response response = request("notes.restoreComment", param).execute();
        return response.root().value().toBoolean();
    }

}