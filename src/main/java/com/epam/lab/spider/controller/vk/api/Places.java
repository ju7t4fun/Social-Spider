package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Place;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Places extends Methods {

    public Places(AccessToken token) {
        super(token);
    }

    /**
     * Добавляет новое место в базу географических мест.
     */
    public int add(Parameters param) throws VKException {
        Response response = request("places.add", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает информацию о местах по их идентификаторам.
     */
    public List<Place> getById(Parameters param) throws VKException {
        Response response = request("places.getById", param).execute();
        return Place.parsePlace(response.root());
    }

    /**
     * Возвращает список мест, найденных по заданным условиям поиска.
     */
    public List<Place> search(Parameters param) throws VKException {
        Response response = request("places.search", param).execute();
        return Place.parsePlace(response.root().child("items").get(0));
    }

    /**
     * Отмечает пользователя в указанном месте.
     */
    public int checkin(Parameters param) throws VKException {
        Response response = request("places.checkin", param).execute();
        return response.root().value().toInt();
    }

    /**
     * Возвращает список отметок пользователей в местах согласно заданным параметрам.
     */
    public List<Place> getCheckins(Parameters param) throws VKException {
        Response response = request("places.getCheckins", param).execute();
        return Place.parsePlace(response.root().child("items").get(0));
    }

    /**
     * Возвращает список всех возможных типов мест.
     */
    public Map<Integer, Type> getTypes() throws VKException {
        Response response = request("places.getTypes", new Parameters()).execute();
        Map<Integer, Type> map = new HashMap<Integer, Type>();
        List<Node> nodes = response.root().child();
        for (final Node node : nodes)
            map.put(node.child("id").get(0).value().toInt(), new Type() {
                @Override
                public String getTitle() {
                    return node.child("title").get(0).value().toString();
                }

                @Override
                public URL getIcon() {
                    return node.child("icon").get(0).value().toURL();
                }

                @Override
                public String toString() {
                    return getTitle();
                }
            });
        return map;
    }

    public interface Type {
        String getTitle();

        URL getIcon();
    }

}
