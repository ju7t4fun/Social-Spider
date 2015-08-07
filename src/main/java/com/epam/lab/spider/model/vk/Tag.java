package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tag extends Model {

    public static final String UID = "uid";
    public static final String TAG_ID = "tag_id";
    public static final String PLACER_ID = "placer_id";
    public static final String TAGGED_NAME = "tagged_name";
    public static final String DATE = "date";
    public static final String VIEWED = "viewed";

    public Tag() {
        super();
    }

    public Tag(Node root) {
        super(root, Tag.class);
    }

    public static List<Tag> parseTag(Node root) {
        List<Tag> tags = new ArrayList<>();
        List<Node> nodes = root.child("tag");
        for (Node node : nodes)
            tags.add(new Tag(node));
        return tags;
    }

    public int getUId() {
        return get(UID).toInt();
    }

    public int getTagId() {
        return get(TAG_ID).toInt();
    }

    public int getPlacerId() {
        return get(PLACER_ID).toInt();
    }

    public String getTaggedName() {
        return get(TAGGED_NAME).toString();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public boolean getViewed() {
        return get(VIEWED).toBoolean();
    }

}
