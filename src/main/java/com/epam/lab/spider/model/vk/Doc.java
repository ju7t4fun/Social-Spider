package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Doc extends Model implements Attachment {

    public static final String ID = "id";
    public static final String OWNER_ID = "owner_id";
    public static final String TITLE = "title";
    public static final String SIZE = "size";
    public static final String EXT = "ext";
    public static final String URL = "url";
    public static final String PHOTO_100 = "photo_100";
    public static final String PHOTO_130 = "photo_130";

    public Doc() {
        super();
    }

    public Doc(Node root) {
        super(root, Doc.class);
    }

    public static List<Doc> parseDoc(Node root) {
        List<Doc> docs = new ArrayList<Doc>();
        List<Node> nodes = root.child("doc");
        for (Node node : nodes)
            docs.add(new Doc(node));
        return docs;
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    @Override
    public Type getType() {
        return Type.DOC;
    }

    @Override
    public int getOwnerId() {
        return get(OWNER_ID).toInt();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public int getSize() {
        return get(SIZE).toInt();
    }

    public String getExt() {
        return get(EXT).toString();
    }

    public URL getUrl() {
        return get(URL).toURL();
    }

    public URL getPhoto100() {
        return get(PHOTO_100).toURL();
    }

    public URL getPhoto130() {
        return get(PHOTO_130).toURL();
    }

}
