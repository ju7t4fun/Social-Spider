package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.util.Date;

public class Album extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
    public static final String SIZE = "size";

    private Photo thumb;

    public Album() {
        super();
    }

    public Album(Node root) {
        super(root, Album.class);
        thumb = new Photo(root.child("thumb").get(0));
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.ALBUM;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public String getDescription() {
        return get(DESCRIPTION).toString();
    }

    public Date getCreated() {
        return get(CREATED).toDate();
    }

    public Date getUpdated() {
        return get(UPDATED).toDate();
    }

    public int getSize() {
        return get(SIZE).toInt();
    }

    public Photo getThumb() {
        return thumb;
    }

}
