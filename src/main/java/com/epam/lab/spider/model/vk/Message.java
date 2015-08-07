package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Message extends Model {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String DATE = "date";
    public static final String READ_STATE = "read_state";
    public static final String OUT = "out";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String GEO_TYPE = "geo.type";
    public static final String GEO_COORDINATES = "geo.coordinates";
    public static final String GEO_PLACE_ID = "geo.place.id";
    public static final String GEO_PLACE_TITLE = "geo.place.title";
    public static final String GEO_PLACE_LATITUDE = "geo.place.latitude";
    public static final String GEO_PLACE_LONGITUDE = "geo.place.longitude";
    public static final String GEO_PLACE_CREATED = "geo.place.created";
    public static final String GEO_PLACE_ICON = "geo.place.icon";
    public static final String GEO_PLACE_COUNTRY = "geo.place.country";
    public static final String GEO_PLACE_CITY = "geo.place.city";
    public static final String MESSAGE_EMOJI = "emoji";
    public static final String EMOJI = "important";
    public static final String DELETED = "deleted";

    public Message() {
        super();
    }

    public Message(Node root) {
        super(root, Message.class);
    }

    public static List<Message> parseMessage(Node root) {
        List<Message> messages = new ArrayList<Message>();
        List<Node> nodes = root.child("message");
        for (Node node : nodes)
            messages.add(new Message(node));
        return messages;
    }

}
