package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Photo extends Model implements Attachment {

    public static final String ID = "id";
    public static final String ALBUM_ID = "album_id";
    public static final String OWNER_ID = "owner_id";
    public static final String USER_ID = "user_id";
    public static final String PHOTO_75 = "photo_75";
    public static final String PHOTO_130 = "photo_130";
    public static final String PHOTO_604 = "photo_604";
    public static final String PHOTO_807 = "photo_807";
    public static final String PHOTO_1280 = "photo_1280";
    public static final String PHOTO_2560 = "photo_2560";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String TEXT = "text";
    public static final String DATE = "date";

    public Photo() {
        super();
    }

    public Photo(Node root) {
        super(root, Photo.class);
    }

    public static List<Photo> parsePhoto(Node root) {
        List<Photo> photos = new ArrayList<Photo>();
        List<Node> nodes = root.child("photo");
        for (Node node : nodes)
            photos.add(new Photo(node));
        return photos;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    public int getAlbumId() {
        return get(ALBUM_ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.PHOTO;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public int getUserId() {
        return get(USER_ID).toInt();
    }

    public URL getPhoto75() {
        return get(PHOTO_75).toURL();
    }

    public URL getPhoto130() {
        return get(PHOTO_130).toURL();
    }

    public URL getPhoto604() {
        return get(PHOTO_604).toURL();
    }

    public URL getPhoto807() {
        return get(PHOTO_807).toURL();
    }

    public URL getPhoto1280() {
        return get(PHOTO_1280).toURL();
    }

    public URL getPhoto2560() {
        return get(PHOTO_2560).toURL();
    }

    public int getWidth() {
        return get(WIDTH).toInt();
    }

    public int getHeight() {
        return get(HEIGHT).toInt();
    }

    public String getText() {
        return get(TEXT).toString();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public static class Album extends Model {

        public static final String ID = "id";
        public static final String THUMB_ID = "thumb_id";
        public static final String OWNER_ID = "owner_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String CREATED = "created";
        public static final String UPDATED = "updated";
        public static final String SIZE = "size";
        public static final String PRIVACY = "privacy";
        public static final String COMMENT_PRIVACY = "comment_privacy";
        public static final String UPLOAD_BY_ADMINS_ONLY = "upload_by_admins_only";
        public static final String COMMENTS_DISABLED = "comments_disabled";
        public static final String CAN_UPLOAD = "can_upload";

        public Album() {
            super();
        }

        public Album(Node root) {
            super(root, Album.class);
        }

        public static List<Album> parseAlbum(Node root) {
            List<Album> albums = new ArrayList<Album>();
            List<Node> nodes = root.child("album");
            for (Node node : nodes)
                albums.add(new Album(node));
            return albums;
        }

    }

}
