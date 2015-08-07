package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Date;

/**
 * @author Yura Kovalik
 */
public interface Event extends PersistenceIdentifiable {
    Integer getId();


    Integer getUserId();

    void setUserId(Integer userId);

    Type getType();

    void setType(Type type);

    Date getTime();

    void setTime(Date time);

    String getTitle();

    void setTitle(String title);

    String getMessage();

    void setMessage(String message);

    Boolean getShown();

    void setShown(Boolean shown);

    enum Type {
        INFO, SUCCESS, WARN, ERROR
    }
}
