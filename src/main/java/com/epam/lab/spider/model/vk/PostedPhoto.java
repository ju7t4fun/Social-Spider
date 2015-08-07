package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;

public class PostedPhoto extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String PHOTO_130 = "photo_130";
    public static final String PHOTO_604 = "photo_604";

    public PostedPhoto() {
        super();
    }

    public PostedPhoto(Node root) {
        super(root, PostedPhoto.class);
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.POSTED_PHOTO;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public URL getPhoto130() {
        return get(PHOTO_130).toURL();
    }

    public URL getPhoto604() {
        return get(PHOTO_604).toURL();
    }

}
