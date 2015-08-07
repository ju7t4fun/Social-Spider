package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Date;

/**
 * @author Yura Kovalik
 */
public interface Profile extends PersistenceIdentifiable {
    boolean isExpired();

    Integer getId();


    Integer getUserId();

    void setUserId(Integer userId);

    Integer getVkId();

    void setVkId(Integer vkId);

    String getAccessToken();

    void setAccessToken(String accessToken);

    Date getExtTime();

    void setExtTime(Date extTime);

    Integer getAppId();

    void setAppId(Integer appId);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    String getName();

    void setName(String name);
}
