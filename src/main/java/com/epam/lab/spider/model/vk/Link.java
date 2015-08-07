package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Link extends Model implements Attachment {

    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_SRC = "image_src";
    public static final String PREVIEW_PAGE = "preview_page";
    public static final String PREVIEW_URL = "preview_url";

    public Link() {
        super();
    }

    public Link(Node root) {
        super(root, Link.class);
    }

    public static List<Link> parseLink(Node root) {
        List<Link> links = new ArrayList<Link>();
        List<Node> nodes = root.child("link");
        for (Node node : nodes)
            links.add(new Link(node));
        return links;
    }

    public URL getUrl() {
        return get(URL).toURL();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public String getDescription() {
        return get(DESCRIPTION).toString();
    }

    public String getImageSrc() {
        return get(IMAGE_SRC).toString();
    }

    public String getPreviewPage() {
        return get(PREVIEW_PAGE).toString();
    }

    public String getPreviewUrl() {
        return get(PREVIEW_URL).toString();
    }

    @Override
    public String toString() {
        return "com.vk.model.Link {url='" + getUrl() + "'}";
    }

    @Override
    public Type getType() {
        return Type.LINK;
    }

    @Override
    public int getOwnerId() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    public enum Status {

        NOT_BANNED, BANNED, PROCESSING;

        public static Status getByName(String name) {
            Status[] status = Status.values();
            for (Status args : status)
                if (args.toString().equals(name))
                    return args;
            return null;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.US);
        }

    }


}
