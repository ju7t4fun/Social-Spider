package com.epam.lab.spider.controller.utils;

import com.epam.lab.spider.model.db.entity.Event;
import com.epam.lab.spider.model.db.service.EventService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class EventLogger {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final EventService service = factory.create(EventService.class);
    private static Map<Integer, EventLogger> instances = new HashMap<>();
    private final int userId;

    private EventLogger(int userId) {
        this.userId = userId;
    }

    public static EventLogger getLogger(int userId) {
        if (!instances.containsKey(userId)) {
            instances.put(userId, new EventLogger(userId));
        }
        return instances.get(userId);
    }

    public boolean info(String messages) {
        return createEvent(Event.Type.INFO, messages);
    }

    public boolean warn(String messages) {
        return createEvent(Event.Type.WARN, messages);
    }

    public boolean error(String messages) {
        return createEvent(Event.Type.ERROR, messages);
    }

    private boolean createEvent(Event.Type type, String message) {
        Event event = new Event();
        event.setType(type);
        event.setUserId(userId);
        event.setMessage(message);
        return service.insert(event);
    }

}