package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment extends Model {

    public static final String ID = "id";
    public static final String FROM_ID = "from_id";
    public static final String DATE = "date";
    public static final String TEXT = "text";
    public static final String REPLY_TO_USER = "reply_to_user";
    public static final String REPLY_TO_COMMENT = "reply_to_comment";
    public static final String LIKES_COUNT = "likes.count";
    public static final String LIKES_USER_LIKES = "likes.user_likes";
    public static final String LIKES_CAN_LIKE = "likes.can_like";

    private List<Attachment> attachments;

    public Comment() {
        super();
    }

    public Comment(Node root) {
        super(root, Comment.class);
        attachments = AttachmentFactory.parseAttachment(root);
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

    public int getFromId() {
        return get(FROM_ID).toInt();
    }

    public Date getDate() {
        return get(DATE).toDate();
    }

    public String getText() {
        return get(TEXT).toString();
    }

    public int getReplyToUser() {
        return get(REPLY_TO_USER).toInt();
    }

    public int getReplyToComment() {
        return get(REPLY_TO_COMMENT).toInt();
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Likes getLikes() {
        return new Likes() {
            @Override
            public int getCount() {
                return get(LIKES_COUNT).toInt();
            }

            @Override
            public boolean getUserLikes() {
                return get(LIKES_USER_LIKES).toBoolean();
            }

            @Override
            public boolean getCanLike() {
                return get(LIKES_CAN_LIKE).toBoolean();
            }
        };
    }

    public interface Likes {
        int getCount();

        boolean getUserLikes();

        boolean getCanLike();
    }

}
