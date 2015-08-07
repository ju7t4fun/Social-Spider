package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

/**
 * @author Yura Kovalik
 * @author Oleksandra Lobanok
 */
public interface Attachment extends PersistenceIdentifiable {

    Mode getMode();

    void setMode(Mode mode);

    Integer getId();

    String getPayload();

    void setPayload(String payload);

    Integer getPostId();

    void setPostId(Integer postId);

    Type getType();

    void setType(Type type);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Post getPost();

    void setPost(Post post);

    enum Type {
        AUDIO, PHOTO, VIDEO, DOC, OTHER
    }

    enum Mode {
        URL, CODE
    }

}
