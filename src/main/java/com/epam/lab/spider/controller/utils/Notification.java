package com.epam.lab.spider.controller.utils;


import com.epam.lab.spider.model.db.entity.Event;
import org.json.simple.JSONObject;

/**
 * Created by Boyarsky Vitaliy on 20.06.2015.
 */
public class Notification {

    public JSONObject basic(Event event) {
        JSONObject json = new JSONObject();
        json.put("message", event.getMessage());
        json.put("title", event.getTitle());
        json.put("typeNotification", event.getType().toString());
        json.put("type", "basic");
        return json;
    }

}
