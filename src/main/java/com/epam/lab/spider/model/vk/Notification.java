package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.List;

public class Notification extends Model {

    public Notification() {
        super();
    }

    public Notification(Node root) {
        super(root, Notification.class);
    }

    public static List<Notification> parseNotification(Node root) {
        return null;
    }

}
