package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.service.AttachmentService;
import com.epam.lab.spider.model.service.ServiceFactory;

import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Post {

    private Integer id;
    private String message;
    private List<Attachment> attachments = null;
    private Boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Attachment> getAttachments() {
        if (attachments == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            AttachmentService service = factory.create(AttachmentService.class);
            attachments = service.getByPostId(id);
        }
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", attachments=" + attachments +
                ", deleted=" + deleted +
                '}';
    }

}
