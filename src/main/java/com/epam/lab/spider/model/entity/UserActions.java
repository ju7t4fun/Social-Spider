package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

/**
 * @author Yura Kovalik
 */
public interface UserActions extends PersistenceIdentifiable {
    Integer getId();


    Integer getUserId();

    void setUserId(Integer userId);

    Integer getTaskExecuteCount();

    void setTaskExecuteCount(Integer taskRun);

    Integer getPostExecuteCount();

    void setPostExecuteCount(Integer postCount);

    Integer getAttachmentExecuteCount();

    void setAttachmentExecuteCount(Integer attachmentCount);

    Integer getAttachmentTraffic();

    void setAttachmentTraffic(Integer attachmentTraffic);
}
