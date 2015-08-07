package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Topic extends Model {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CREATED = "created";
    public static final String CREATED_BY = "created_by";
    public static final String UPDATED = "updated";
    public static final String UPDATED_BY = "updated_by";
    public static final String IS_CLOSED = "is_closed";
    public static final String IS_FIXED = "is_fixed";
    public static final String COMMENTS = "comments";
    public static final String FIRST_COMMENT = "first_comment";
    public static final String LAST_COMMENT = "last_comment";

    public Topic() {
        super();
    }

    public Topic(Node root) {
        super(root, Topic.class);
    }

    public static List<Topic> parseTopic(Node root) {
        List<Topic> topics = new ArrayList<Topic>();
        List<Node> nodes = root.child("topic");
        for (Node node : nodes)
            topics.add(new Topic(node));
        return topics;
    }
}
