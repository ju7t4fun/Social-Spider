package com.epam.lab.spider.model.db.entity;

import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class Event {

    private Integer id;
    private Integer userId;
    private Type type = Type.INFO;
    private Date time;
    private String title;
    private String message;
    private Boolean shown = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getShown() {
        return shown;
    }

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

    public enum Type {
        INFO, SUCCESS, WARN, ERROR
    }

}
