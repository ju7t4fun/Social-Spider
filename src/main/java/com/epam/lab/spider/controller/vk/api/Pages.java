package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Page;

import java.util.List;

public class Pages extends Methods {

    public Pages(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает информацию о вики-странице.
     */
    public Page get(Parameters param) throws VKException {
        Response response = request("pages.get", param).execute();
        return new Page(response.root());
    }

    /**
     * Сохраняет текст вики-страницы.
     */
    public int save(Parameters param) throws VKException {
        Response response = request("pages.save", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Сохраняет новые настройки доступа на чтение и редактирование вики-страницы.
     */
    public int saveAccess(Parameters param) throws VKException {
        Response response = request("pages.saveAccess", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает список всех старых версий вики-страницы.
     */
    public List<Page.Version> getHistory(Parameters param) throws VKException {
        Response response = request("pages.getHistory", param).execute();
        return Page.Version.parseVersion(response.root().child("items").get(0));
    }

    /**
     * Возвращает список вики-страниц в группе.
     */
    public List<Page> getTitles(Parameters param) throws VKException {
        Response response = request("pages.getTitles", param).execute();
        return Page.parsePage(response.root().child("items").get(0));
    }

    /**
     * Возвращает текст одной из старых версий страницы.
     */
    public List<Page> getVersion(Parameters param) throws VKException {
        Response response = request("pages.getVersion", param).execute();
        return Page.parsePage(response.root().child("items").get(0));
    }

    /**
     * Возвращает html-представление вики-разметки.
     */
    public String parseWiki(Parameters param) throws VKException {
        Response response = request("pages.parseWiki", param).execute();
        return response.root().value().toString();
    }

    /**
     * Позволяет очистить кеш отдельных внешних страниц, которые могут быть прикреплены к записям ВКонтакте.
     * После очистки кеша при последующем прикреплении ссылки к записи, данные о странице будут обновлены.
     */
    public boolean clearCache(Parameters param) throws VKException {
        Response response = request("pages.clearCache", param).execute();
        return response.root().value().toBoolean();
    }

}
