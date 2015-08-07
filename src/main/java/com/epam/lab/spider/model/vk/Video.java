package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Video extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DURATION = "duration";
    public static final String LINK = "link";
    public static final String PHOTO_130 = "photo_130";
    public static final String PHOTO_320 = "photo_320";
    public static final String PHOTO_640 = "photo_640";
    public static final String DATE = "date";
    public static final String ADDING_DATE = "adding_date";
    public static final String VIEWS = "views";
    public static final String COMMENTS = "comments";
    public static final String PLAYER = "player";

    public static final String PLACER_ID = "placer_id";
    public static final String TAG_CREATED = "tag_created";
    public static final String TAG_ID = "tag_id";

    public Video() {
        super();
    }

    public Video(Node root) {
        super(root, Video.class);
    }

    public static List<Video> parseVideo(Node root) {
        List<Video> videos = new ArrayList<Video>();
        List<Node> nodes = root.child("video");
        for (Node node : nodes)
            videos.add(new Video(node));
        return videos;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.VIDEO;
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

    public int getDuration() {
        return get(DURATION).toInt();
    }

    public String getLink() {
        return get(LINK).toString();
    }

    public URL getPhoto130() {
        return get(PHOTO_130).toURL();
    }

    public URL getPhoto320() {
        return get(PHOTO_320).toURL();
    }

    public URL getPhoto640() {
        return get(PHOTO_640).toURL();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public Date getAddingDate() {
        return get(ADDING_DATE).toDate();
    }

    public int getViews() {
        return get(VIEWS).toInt();
    }

    public int getComments() {
        return get(COMMENTS).toInt();
    }

    public URL getPlayer() {
        return get(PLAYER).toURL();
    }

    public int getPlacerId() {
        return get(PLACER_ID).toInt();
    }

    public Date getTagCreated() {
        return get(TAG_CREATED).toDate();
    }

    public int getTagId() {
        return get(TAG_ID).toInt();
    }

}
