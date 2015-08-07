package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Event;

import java.util.Date;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class EventSynchronized extends EntitySynchronized<Event> implements Event {
    public EventSynchronized(Event entity) {
        super(entity);
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

    public Type getType() {
        return getEntity().getType();
    }

    public void setType(Type type) {
        setUnSynchronized();
        getEntity().setType(type);
    }

    public Date getTime() {
        return getEntity().getTime();
    }

    public void setTime(Date time) {
        setUnSynchronized();
        getEntity().setTime(time);
    }

    public String getTitle() {
        return getEntity().getTitle();
    }

    public void setTitle(String title) {
        setUnSynchronized();
        getEntity().setTitle(title);
    }

    public String getMessage() {
        return getEntity().getMessage();
    }

    public void setMessage(String message) {
        setUnSynchronized();
        getEntity().setMessage(message);
    }

    public Boolean getShown() {
        return getEntity().getShown();
    }

    public void setShown(Boolean shown) {
        setUnSynchronized();
        getEntity().setShown(shown);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }
}
