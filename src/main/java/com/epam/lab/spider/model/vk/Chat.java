package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Model {

    public static final String ID = "id";
    public static final String USER_ID = "type";
    public static final String DATE = "title";
    public static final String READ_STATE = "admin_id";
    public static final String OUT = "users";

    public Chat() {
        super();
    }

    public Chat(Node root) {
        super(root, Chat.class);
    }

    public static List<Chat> parseChat(Node root) {
        List<Chat> chats = new ArrayList<>();
        List<Node> nodes = root.child("chat");
        for (Node node : nodes)
            chats.add(new Chat(node));
        return chats;
    }

}
