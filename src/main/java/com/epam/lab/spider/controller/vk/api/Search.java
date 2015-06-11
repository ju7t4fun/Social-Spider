package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Group;
import com.epam.lab.spider.model.vk.Model;
import com.epam.lab.spider.model.vk.User;

import java.util.ArrayList;
import java.util.List;

public class Search extends Methods {

    public Search(AccessToken token) {
        super(token);
    }

    /**
     * Метод позволяет получить результаты быстрого поиска по произвольной подстроке
     */
    public List<Model> getHints(Parameters param) throws VKException {
        Response response = request("search.getHints", param).execute();
        List<Model> object = new ArrayList<Model>();
        List<Node> nodes = response.root().child("item");
        for (Node node : nodes) {
            if (node.hasChild("profile"))
                object.add(new User(node.child("profile").get(0)));
            if (node.hasChild("group"))
                object.add(new Group(node.child("group").get(0)));
        }
        return object;
    }

}
