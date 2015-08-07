package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.UserActions;

/**
 * @author Yura Kovalik
 */
public class UserActionsImpl implements UserActions, PersistenceIdentificationChangeable {
    private Integer id;
    private Integer userId;
    private Integer taskRun = 0;
    private Integer postCount = 0;
    private Integer attachmentCount = 0;
    private Integer attachmentTraffic = 0;

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
    public Integer getTaskExecuteCount() {
        return taskRun;
    }

    @Override
    public void setTaskExecuteCount(Integer taskRun) {
        this.taskRun = taskRun;
    }

    @Override
    public Integer getPostExecuteCount() {
        return postCount;
    }

    @Override
    public void setPostExecuteCount(Integer postCount) {
        this.postCount = postCount;
    }

    @Override
    public Integer getAttachmentExecuteCount() {
        return attachmentCount;
    }

    @Override
    public void setAttachmentExecuteCount(Integer attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    @Override
    public Integer getAttachmentTraffic() {
        return attachmentTraffic;
    }

    @Override
    public void setAttachmentTraffic(Integer attachmentTraffic) {
        this.attachmentTraffic = attachmentTraffic;
    }
}
