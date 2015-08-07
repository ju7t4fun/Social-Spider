package com.epam.lab.spider.controller.utils;


import com.epam.lab.spider.model.entity.Event;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class Notification {

    private Notification() {
        super();
    }

    public static JSONObject basic(Event event) {
        JSONObject json = new JSONObject();
        json.put("type", "basic");
        json.put("typeNotification", event.getType().toString());
        json.put("title", event.getTitle());
        json.put("message", event.getMessage());
        return json;
    }

    public static JSONObject list(List<Event> events) {
        JSONObject json = new JSONObject();
        json.put("type", "list");
        json.put("title", "Group " + events.get(0).getType().toString().toLowerCase());
        json.put("typeNotification", events.get(0).getType().toString());
        json.put("message", "");
        JSONArray list = new JSONArray();
        for (Event event : events) {
            JSONObject obj = new JSONObject();
            obj.put("title", new SimpleDateFormat("MM.dd E  HH:mm").format(event.getTime()));
            obj.put("message", event.getTitle());
            list.put(obj);
        }
        json.put("items", list);
        return json;
    }

}
