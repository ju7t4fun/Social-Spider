package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoAlbum extends Model {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String TITLE = "title";
    public static final String COUNT = "count";
    public static final String PHOTO_320 = "photo_320";
    public static final String PHOTO_169 = "photo_169";
    public static final String UPDATED_TIME = "updated_time";

    public VideoAlbum() {
        super();
    }

    public VideoAlbum(Node root) {
        super(root, VideoAlbum.class);
    }

    public static List<VideoAlbum> parseVideoAlbum(Node root) {
        List<VideoAlbum> videos = new ArrayList<VideoAlbum>();
        List<Node> nodes = root.child("album");
        for (Node node : nodes)
            videos.add(new VideoAlbum(node));
        return videos;
    }

    public int getId() {
        return get(ID).toInt();
    }

    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public int getCount() {
        return get(COUNT).toInt();
    }

    public URL getPhoto320() {
        return get(PHOTO_320).toURL();
    }

    public URL getPhoto169() {
        return get(PHOTO_169).toURL();
    }

    public Date getUpdatedTime() {
        return get(UPDATED_TIME).toDate();
    }

}
