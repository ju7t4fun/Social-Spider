package com.epam.lab.spider.controller.utils;

import com.epam.lab.spider.model.db.entity.Event;
import com.epam.lab.spider.model.db.service.EventService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class EventLogger {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final EventService service = factory.create(EventService.class);
    private static Map<Integer, EventLogger> instances = new HashMap<>();
    private static Receiver receiver;
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

    public boolean info(String title, String messages) {
        return createEvent(Event.Type.INFO, title, messages);
    }

    public boolean success(String title, String messages) {
        return createEvent(Event.Type.SUCCESS, title, messages);
    }

    public boolean warn(String title, String messages) {
        return createEvent(Event.Type.WARN, title, messages);
    }

    public boolean error(String title, String messages) {
        return createEvent(Event.Type.ERROR, title, messages);
    }

    private boolean createEvent(Event.Type type, String title, String message) {
        Event event = new Event();
        event.setType(type);
        event.setUserId(userId);
        event.setTitle(title);
        event.setMessage(message);
        if (sendEvent(event)) {
            event.setShown(true);
        }
        return service.insert(event);
    }

    private boolean sendEvent(Event event) {
        return receiver != null && receiver.send(userId, Notification.basic(event).toString());
    }

    public static class EventSender implements Sender {

        @Override
        public void accept(Receiver receiver) {
            EventLogger.receiver = receiver;
        }

        @Override
        public void history(int clientId) {
            List<Event> events = service.getByShownUserId(clientId);
            Map<Event.Type, List<Event>> eventGroupByType = new HashMap<>();
            for (Event event : events) {
                List list;
                if (eventGroupByType.containsKey(event.getType()))
                    list = eventGroupByType.get(event.getType());
                else
                    list = new ArrayList();
                list.add(event);
                eventGroupByType.put(event.getType(), list);
            }
            for (Event.Type eventType : eventGroupByType.keySet()) {
                List<Event> allEvent = eventGroupByType.get(eventType);
                List<Event> list = new ArrayList<>();
                for (Event event : allEvent) {
                    if (list.size() == 5) {
                        receiver.send(clientId, Notification.list(list).toString());
                        list = new ArrayList<>();
                    }
                    list.add(event);
                }
                if (list.size() > 0)
                    receiver.send(clientId, Notification.list(list).toString());
            }
            service.markAsShowByUserId(clientId);
        }

    }

}