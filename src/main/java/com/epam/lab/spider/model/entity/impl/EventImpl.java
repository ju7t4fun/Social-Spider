package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Event;

import java.util.Date;

/**
 * @author Boyarsky Vitaliy
 */
public class EventImpl implements Event, PersistenceIdentificationChangeable {

    private Integer id;
    private Integer userId;
    private Type type = Type.INFO;
    private Date time;
    private String title;
    private String message;
    private Boolean shown = false;

    protected EventImpl() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Boolean getShown() {
        return shown;
    }

    @Override
    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", shown=" + shown +
                '}';
    }

}
