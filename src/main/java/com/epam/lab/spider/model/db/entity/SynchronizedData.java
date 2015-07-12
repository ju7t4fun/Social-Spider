package com.epam.lab.spider.model.db.entity;

/**
 * Created by hell-engine on 7/12/2015.
 */
public interface SynchronizedData {

    public abstract void syncWith(SynchronizedData otherSync);

    public abstract Integer getPostOffset();

    public abstract void setPostOffset(Integer postOffset);

    public abstract Integer getPostVkId();

    public abstract void setPostVkId(Integer postVkId);

    public abstract Integer getWallId();

    public abstract void setWallId(Integer wallId);

    public abstract Integer getTaskId();

    public abstract void setTaskId(Integer taskId);
}
