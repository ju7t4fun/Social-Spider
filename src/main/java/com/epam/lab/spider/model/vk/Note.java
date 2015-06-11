package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note extends Model implements Attachment {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String DATE = "date";
    public static final String COMMENTS = "comments";
    public static final String READ_COMMENTS = "read_comments";
    public static final String VIEW_URL = "view_url";
    public static final String PRIVACY = "privacy";
    public static final String COMMENT_PRIVACY = "comment_privacy";
    public static final String CAN_COMMENT = "can_comment";

    public Note() {
        super();
    }

    public Note(Node root) {
        super(root, Note.class);
    }

    public static List<Note> parseNote(Node root) {
        List<Note> notes = new ArrayList<Note>();
        List<Node> nodes = root.child("note");
        for (Node node : nodes)
            notes.add(new Note(node));
        return notes;
    }

    @Override
    public Type getType() {
        return Type.NOTE;
    }

    @Override
    public int getOwnerId() {
        return getUserId();
    }

    @Override
    public int getId() {
        return get(ID).toInt();
    }

    public int getUserId() {
        return get(USER_ID).toInt();
    }

    public String getTitle() {
        return get(TITLE).toString();
    }

    public String getText() {
        return get(TEXT).toString();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public int getComments() {
        return get(COMMENTS).toInt();
    }

    public int getReadComments() {
        return get(READ_COMMENTS).toInt();
    }

    public URL getViewUrl() {
        return get(VIEW_URL).toURL();
    }

    public byte getPrivacy() {
        return get(PRIVACY).toByte();
    }

    public byte getCommentPrivacy() {
        return get(COMMENT_PRIVACY).toByte();
    }

    public boolean canComment() {
        return get(CAN_COMMENT).toBoolean();
    }

    public static class Comment extends Model {

        public static final String ID = "id";
        public static final String UID = "uid";
        public static final String NID = "nid";
        public static final String OID = "oid";
        public static final String DATE = "date";
        public static final String MESSAGE = "message";
        public static final String REPLY_TO = "reply_to";

        public Comment() {
            super();
        }

        public Comment(Node root) {
            super(root, Comment.class);
        }

        public static List<Comment> parseComment(Node root) {
            List<Comment> comments = new ArrayList<Comment>();
            List<Node> nodes = root.child("comment");
            for (Node node : nodes)
                comments.add(new Comment(node));
            return comments;
        }

        public int getId() {
            return get(ID).toInt();
        }

        public int getUid() {
            return get(UID).toInt();
        }

        public int getNid() {
            return get(NID).toInt();
        }

        public int getOid() {
            return get(OID).toInt();
        }

        public Date getDate() {
            return get(DATE).toDate();
        }

        public String getMessage() {
            return get(MESSAGE).toString();
        }

        public int getReplyTo() {
            return get(REPLY_TO).toInt();
        }

    }

}
