package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Boyarsky Vitaliy
 */
public class MessageImpl implements Message, PersistenceIdentificationChangeable {

    private Integer id;
    private Integer userId;
    private String text;
    private Date date = new Date();
    private Type type = Type.TO_ADMIN;
    private Boolean deleted = false;
    private Boolean read = false;

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
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getFormatData() {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
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
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Boolean getRead() {
        return read;
    }

    @Override
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

}
