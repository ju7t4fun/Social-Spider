package com.epam.lab.spider.model.db.entity;

import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 23.06.2015.
 */
public class Message {

    private Integer id;
    private Integer userId;
    private String text;
    private Date date = new Date();
    private Type type = Type.TO_ADMIN;
    private Boolean deleted = false;
    private Boolean read = false;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", deleted=" + deleted +
                ", read=" + read +
                '}';
    }

    public enum Type {
        TO_ADMIN, TO_USER
    }

}
