package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class AttachmentFactory {

    public static List<Attachment> parseAttachment(Node root) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        if (root.hasChild("attachments")) {
            List<Node> nodes = root.child("attachments").get(0).child();
            for (Node node : nodes) {
                attachments.add(AttachmentFactory.getInstance(node));
            }
        }
        return attachments;
    }

    public static Attachment getInstance(Node root) {
        String type = root.child("type").get(0).value().toString();
        Node current = root.child(type).get(0);
        switch (Attachment.Type.getByName(type)) {
            case PHOTO:
                return new Photo(current);
            case POSTED_PHOTO:
                return new PostedPhoto(current);
            case VIDEO:
                return new Video(current);
            case AUDIO:
                return new Audio(current);
            case DOC:
                return new Doc(current);
            case GRAFFITI:
                return new Graffiti(current);
            case LINK:
                return new Link(current);
            case NOTE:
                return new Note(current);
            case APP:
                return new App(current);
            case POLL:
                return new Poll(current);
            case PAGE:
                return new Page(current);
            case ALBUM:
                return new Album(current);
            case PHOTOS_LIST:
                return null;
            default:
                return null;
        }
    }

}
