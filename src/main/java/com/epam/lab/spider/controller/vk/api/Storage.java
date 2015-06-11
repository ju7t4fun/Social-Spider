package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage extends Methods {

    public Storage(AccessToken token) {
        super(token);
    }

    /**
     * Возвращает значение нескольких переменных, название которых передано в параметре key.
     */
    public String get(Parameters param) throws VKException {
        Response response = request("storage.get", param).execute();
        return response.root().value().toString();
    }

    /**
     * Возвращает значение нескольких переменных, название которых передано в параметре key.
     */
    public Map<String, String> getEx(Parameters param) throws VKException {
        Response response = request("storage.get", param).execute();
        Map<String, String> keys = new HashMap<String, String>();
        List<Node> nodes = response.root().child("item");
        for (Node node : nodes)
            keys.put(node.parse("key"), node.parse("value"));
        return keys;
    }

    /**
     * Сохраняет значение переменной, название которой передано в параметре key.
     */
    public boolean set(Parameters param) throws VKException {
        Response response = request("storage.set", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Удаляет значение переменной, название которой передано в параметре key.
     */
    public boolean delete(Parameters param) throws VKException {
        param.add("value", "");
        Response response = request("storage.set", param).execute();
        return response.root().value().toBoolean();
    }

    /**
     * Возвращает названия всех переменных.
     */
    public List<String> getKeys(Parameters param) throws VKException {
        Response response = request("storage.getKeys", param).execute();
        List<String> keys = new ArrayList<String>();
        List<Node> nodes = response.root().child("item");
        for (Node node : nodes)
            keys.add(node.value().toString());
        return keys;
    }

}
