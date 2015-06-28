package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.db.service.AttachmentService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Post {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final AttachmentService service = factory.create(AttachmentService.class);

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
        if (attachments == null) {
            if (id == null)
                attachments = new HashSet<>();
            else
                attachments = new HashSet<>(service.getByPostId(id));
        }
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
