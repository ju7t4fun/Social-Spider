package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App extends Model implements Attachment {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHOTO_130 = "photo_130";
    public static final String PHOTO_604 = "photo_604";
    public static final String TITLE = "title";
    public static final String SCREEN_NAME = "screen_name";
    public static final String DESCRIPTION = "description";
    public static final String ICON_100 = "icon_100";
    public static final String ICON_200 = "icon_200";
    public static final String ICON_75 = "icon_75";
    public static final String ICON_50 = "icon_50";
    public static final String ICON_25 = "icon_25";
    public static final String ICON_15 = "icon_16";
    public static final String BANNER_186 = "banner_186";
    public static final String TYPE_APP = "type.app";
    public static final String TYPE_GAME = "type.game";
    public static final String TYPE_SITE = "type.site";
    public static final String TYPE_STANDALONE = "type.standalone";
    public static final String SECTION = "section";
    public static final String AUTHOR_URL = "author_url";
    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_GROUP = "author_group";
    public static final String MEMBERS_COUNT = "members_count";
    public static final String PUBLISHED_DATE = "published_date";
    public static final String CATALOG_POSITION = "catalog_position";
    public static final String SCREENSHOTS = "screenshots";
    public static final String INTERNATIONAL = "international";

    public App() {
        super();
    }

    public App(Node root) {
        super(root, App.class);
    }

    public static List<App> parseApp(Node root) {
        List<App> apps = new ArrayList<App>();
        List<Node> nodes = root.child("app");
        for (Node node : nodes)
            apps.add(new App(node));
        return apps;
    }

    @Override
    public Type getType() {
        return Type.APP;
    }

    @Override
    public int getOwnerId() {
        return 0;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    public String getName() {
        return get(NAME).toString();
    }

    public URL getPhoto130() {
        return get(PHOTO_130).toURL();
    }

    public URL getPhoto604() {
        return get(PHOTO_604).toURL();
    }

}
