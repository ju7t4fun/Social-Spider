package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Message;

import java.util.Date;


/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class MessageSynchronized extends EntitySynchronized<Message> implements Message {
    public MessageSynchronized(Message entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public String getText() {
        return getEntity().getText();
    }

    public void setText(String text) {
        setUnSynchronized();
        getEntity().setText(text);
    }

    public String getFormatData() {
        return getEntity().getFormatData();
    }

    public Date getDate() {
        return getEntity().getDate();
    }

    public void setDate(Date date) {
        setUnSynchronized();
        getEntity().setDate(date);
    }

    public Type getType() {
        return getEntity().getType();
    }

    public void setType(Type type) {
        setUnSynchronized();
        getEntity().setType(type);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public Boolean getRead() {
        return getEntity().getRead();
    }

    public void setRead(Boolean read) {
        setUnSynchronized();
        getEntity().setRead(read);
    }
}
