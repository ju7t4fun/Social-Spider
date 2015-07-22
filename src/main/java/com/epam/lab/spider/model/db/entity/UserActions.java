package com.epam.lab.spider.model.db.entity;

/**
 * Created by hell-engine on 7/21/2015.
 */
public class UserActions {
    private Integer id;
    private Integer userId;
    private Integer taskRun = 0;
    private Integer postCount = 0;
    private Integer attachmentCount = 0;
    private Integer attachmentTraffic = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskExecuteCount() {
        return taskRun;
    }

    public void setTaskExecuteCount(Integer taskRun) {
        this.taskRun = taskRun;
    }

    public Integer getPostExecuteCount() {
        return postCount;
    }

    public void setPostExecuteCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getAttachmentExecuteCount() {
        return attachmentCount;
    }

    public void setAttachmentExecuteCount(Integer attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    public Integer getAttachmentTraffic() {
        return attachmentTraffic;
    }

    public void setAttachmentTraffic(Integer attachmentTraffic) {
        this.attachmentTraffic = attachmentTraffic;
    }
}
