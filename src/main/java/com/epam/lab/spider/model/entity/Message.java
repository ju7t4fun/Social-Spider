package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Date;

/**
 * @author Yura Kovalik
 */
public interface Message extends PersistenceIdentifiable {
    Integer getId();


    Integer getUserId();

    void setUserId(Integer userId);

    String getText();

    void setText(String text);

    String getFormatData();

    Date getDate();

    void setDate(Date date);

    Type getType();

    void setType(Type type);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Boolean getRead();

    void setRead(Boolean read);

    enum Type {
        TO_ADMIN, TO_USER
    }
}
