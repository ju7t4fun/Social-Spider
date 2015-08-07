package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;

import java.util.Set;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class PostSynchronized extends EntitySynchronized<Post> implements Post {
    public PostSynchronized(Post entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Integer getId() {

        return getEntity().getId();
    }

    public Set<Attachment> getAttachments() {
        return getEntity().getAttachments();
    }

    public void setAttachments(Set<Attachment> attachments) {
        setUnSynchronized();
        getEntity().setAttachments(attachments);
    }

    public boolean addAttachment(Attachment attachment) {
        return getEntity().addAttachment(attachment);
    }

    public boolean removeAttachment(Attachment attachment) {
        return getEntity().removeAttachment(attachment);
    }

    public String getMessage() {
        return getEntity().getMessage();
    }

    public void setMessage(String message) {
        setUnSynchronized();
        getEntity().setMessage(message);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }
}
