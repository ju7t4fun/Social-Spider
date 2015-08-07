package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;

public class AttachmentSynchronized extends EntitySynchronized<Attachment> implements Attachment {

    public AttachmentSynchronized(Attachment attachment) {
        super(attachment);
    }

    public Mode getMode() {
        return getEntity().getMode();
    }

    public void setMode(Mode mode) {
        if(getMode().equals(mode))return;
        setUnSynchronized();
        getEntity().setMode(mode);
    }

    @Override
    public Integer getId() {
        return getEntity().getId();
    }


    public String getPayload() {
        return getEntity().getPayload();
    }

    public void setPayload(String payload) {
        if(getPayload().equals(payload))return;
        setUnSynchronized();
        getEntity().setPayload(payload);
    }

    public Integer getPostId() {
        if (getEntity().getPost() != null) {
            return getEntity().getPost().getId();
        }
        return getEntity().getPostId();
    }

    public void setPostId(Integer postId) {
        if(getPostId().equals(postId))return;
        setUnSynchronized();
        getEntity().setPostId(postId);
    }

    public Type getType() {
        return getEntity().getType();
    }

    public void setType(Type type) {
        if(getType().equals(type))return;
        setUnSynchronized();
        getEntity().setType(type);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        if(getDeleted().equals(deleted))return;
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public Post getPost() {
        return getEntity().getPost();
    }

    public void setPost(Post post){
        if(getPost().equals(post))return;
        setUnSynchronized();
        getEntity().setPost(post);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }
}