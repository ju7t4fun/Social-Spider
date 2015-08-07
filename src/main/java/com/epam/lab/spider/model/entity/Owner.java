package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

/**
 * @author Yura Kovalik
 */
public interface Owner extends PersistenceIdentifiable {
    Boolean getBanned();

    void setBanned(Boolean banned);

    Integer getUserId();

    void setUserId(Integer userId);

    Integer getId();


    Integer getVkId();

    void setVkId(Integer vkId);

    String getName();

    void setName(String name);

    String getDomain();

    void setDomain(String domain);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
