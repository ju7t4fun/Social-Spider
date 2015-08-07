package com.epam.lab.spider.integration.vk.api;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.Response;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Doc;

import java.util.List;

public class Docs extends Methods {

    public Docs(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает расширенную информацию о документах пользователя или сообщества.
     */
    public List<Doc> get(Parameters param) throws VKException {
        Response response = request("docs.get", param).execute();
        return Doc.parseDoc(response.root().child("items").get(0));
    }

    /**
     * Возвращает информацию о документах по их идентификаторам.
     */
    public List<Doc> getById(Parameters param) throws VKException {
        Response response = request("docs.getById", param).execute();
        return Doc.parseDoc(response.root());
    }

    /**
     * Возвращает адрес сервера для загрузки документов.
     */
    public String getUploadServer(Parameters param) throws VKException {
        Response response = request("docs.getUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toString();
    }

    /**
     * Возвращает адрес сервера для загрузки документов в папку Отправленные,
     * для последующей отправки документа на стену или личным сообщением.
     */
    public String getWallUploadServer(Parameters param) throws VKException {
        Response response = request("docs.getWallUploadServer", param).execute();
        return response.root().child("upload_url").get(0).value().toString();
    }

    /**
     * Сохраняет документ после его успешной загрузки на сервер.
     */
    public List<Doc> save(Parameters param) throws VKException {
        Response response = request("docs.safeSave", param).execute();
        return Doc.parseDoc(response.root().child("items").get(0));
    }

    /**
     * Удаляет документ пользователя или группы.
     */
    public boolean delete(Parameters param) throws VKException {
        Response response = request("docs.delete", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Копирует документ в документы текущего пользователя.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("docs.add", param).execute();
        return response.root().value().toInt();
    }

}