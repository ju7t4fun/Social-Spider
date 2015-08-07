package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends Model {

    public static final String LIST_ID = "list_id";
    public static final String NAME = "name";

    public FriendsList() {
        super();
    }

    public FriendsList(Node root) {
        super(root, FriendsList.class);
    }

    public static List<FriendsList> parseFriendsList(Node root) {
        List<FriendsList> list = new ArrayList<>();
        List<Node> nodes = root.child("list");
        for (Node node : nodes)
            list.add(new FriendsList(node));
        return list;
    }

    public int getListId() {
        return get(LIST_ID).toInt();
    }

    public String getName() {
        return get(NAME).toString();
    }

}
