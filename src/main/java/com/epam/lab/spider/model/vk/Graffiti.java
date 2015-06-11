package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.net.URL;

public class Graffiti extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String PHOTO_200 = "photo_200";
    public static final String PHOTO_586 = "photo_586";

    public Graffiti() {
        super();
    }

    public Graffiti(Node root) {
        super(root, Graffiti.class);
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.GRAFFITI;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public URL getPhoto200() {
        return get(PHOTO_200).toURL();
    }

    public URL getPhoto586() {
        return get(PHOTO_586).toURL();
    }

}
