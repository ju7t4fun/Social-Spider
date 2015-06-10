package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.vk.Widget;

import java.util.List;

public class Widgets extends Methods {

    public Widgets(AccessToken token) {
        super(token);
    }

    /**
     * Получает список комментариев к странице, оставленных через Виджет комментариев.
     */
    public List<Widget.Post> getComments(Parameters param) throws VKException {
        Response response = request("widgets.getComments", param).execute();
        return Widget.Post.parsePost(response.root().child("posts").get(0));
    }

    /**
     * Получает список страниц приложения/сайта, на которых установлен Виджет комментариев или «Мне нравится».
     */
    public List<Widget.Page> getPages(Parameters param) throws VKException {
        Response response = request("widgets.getPages", param).execute();
        return Widget.Page.parsePage(response.root().child("pages").get(0));
    }

}