package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;

import java.util.Set;

/**
 * @author Oleksandra Lobanok
 * @author Yura Kovalik
 */
public class PostImpl implements Post, PersistenceIdentificationChangeable {
    private Integer id;
    private String message;
    private Boolean deleted = false;
    private Integer userId = 1;
    private Set<Attachment> attachments = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean addAttachment(Attachment attachment) {
        attachment.setPost(this);
        return getAttachments().add(attachment);
    }

    public boolean removeAttachment(Attachment attachment) {
        attachment.setPostId(null);
        attachment.setPost(null);
        return getAttachments().remove(attachment);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", attachments=" + attachments +
                '}';
    }
}
