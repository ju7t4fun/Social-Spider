package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Group extends Model {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SCREEN_NAME = "screen_name";
    public static final String IS_CLOSED = "is_closed";
    public static final String DEACTIVATED = "deactivated";
    public static final String IS_ADMIN = "is_admin";
    public static final String ADMIN_LEVEL = "admin_level";
    public static final String IS_MEMBER = "is_member";
    public static final String TYPE = "type";
    public static final String PHOTO_50 = "photo_50";
    public static final String PHOTO_100 = "photo_100";
    public static final String PHOTO_200 = "photo_200";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String PLACE_ID = "place.id";
    public static final String PLACE_TITLE = "place.title";
    public static final String PLACE_LATITUDE = "place.latitude";
    public static final String PLACE_LONGITUDE = "place.longitude";
    public static final String PLACE_TYPE = "place.type ";
    public static final String PLACE_COUNTRY = "place.country";
    public static final String PLACE_CITY = "place.city";
    public static final String PLACE_ADDRESS = "place.address";
    public static final String DESCRIPTION = "description";
    public static final String WIKI_PAGE = "wiki_page";
    public static final String MEMBERS_COUNT = "members_count";
    public static final String COUNTERS_PHOTOS = "counters.photos";
    public static final String COUNTERS_ALBUMS = "counters.albums";
    public static final String COUNTERS_AUDIOS = "counters.audios";
    public static final String COUNTERS_VIDEOS = "counters.videos";
    public static final String COUNTERS_TOPICS = "counters.topics";
    public static final String COUNTERS_DOCS = "counters.docs";
    public static final String START_DATE = "start_date";
    public static final String FINISH_DATE = "finish_date";
    public static final String CAN_POST = "can_post";
    public static final String CAN_SEE_ALL_POSTS = "can_see_all_posts";
    public static final String CAN_UPLOAD_DOC = "can_upload_doc";
    public static final String CAN_UPLOAD_VIDEO = "can_upload_video";
    public static final String CAN_CREATE_TOPIC = "can_create_topic";
    public static final String ACTIVITY = "activity";
    public static final String STATUS = "status";
    public static final String CONTACTS = "contacts";
    public static final String LINKS = "links";
    public static final String FIXED_POST = "fixed_post";
    public static final String VERIFIED = "verified";
    public static final String SITE = "site";
    public static final String MAIN_ALBUM_ID = "main_album_id";
    public static final String INVITED_BY = "invited_by";

    public Group() {
        super();
    }

    public Group(Node root) {
        super(root, Group.class);
    }

    public static List<Group> parseGroup(Node root) {
        List<Group> groups = new ArrayList<Group>();
        List<Node> nodes = root.child("group");
        for (Node node : nodes) {
            groups.add(new Group(node));
        }
        return groups;
    }

}
