package com.epam.lab.spider.model.db.entity;

/**
 * Created by hell-engine on 7/12/2015.
 */
public interface SynchronizedData {

    void syncWith(SynchronizedData otherSync);

    Integer getPostOffset();

    void setPostOffset(Integer postOffset);

    Integer getPostVkId();

    void setPostVkId(Integer postVkId);

    Integer getWallId();

    void setWallId(Integer wallId);

    Integer getTaskId();

    void setTaskId(Integer taskId);
}
