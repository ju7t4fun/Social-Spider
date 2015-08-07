package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

/**
 * @author Yura Kovalik
 */
public interface Filter extends PersistenceIdentifiable {
    Integer getId();



    Integer getLikes();

    void setLikes(Integer likes);

    Integer getRePosts();

    void setRePosts(Integer reposts);

    Integer getComments();

    void setComments(Integer comments);

    Long getMinTime();

    void setMinTime(Long minTime);

    Long getMaxTime();

    void setMaxTime(Long maxTime);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
