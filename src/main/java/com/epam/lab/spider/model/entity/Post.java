package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Set;

/**
 * @author Yura Kovalik
 */
public interface Post extends PersistenceIdentifiable {

    Integer getId();

    Set<Attachment> getAttachments();

    void setAttachments(Set<Attachment> attachments);

    boolean addAttachment(Attachment attachment);

    boolean removeAttachment(Attachment attachment);

    String getMessage();

    void setMessage(String message);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Integer getUserId();

    void setUserId(Integer userId);

}
