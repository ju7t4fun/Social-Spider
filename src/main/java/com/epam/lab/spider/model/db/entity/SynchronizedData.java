package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.vk.*;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class SynchronizedData {



    private Integer postOffset;
    private Integer postVkId;
    private Integer wallId;
    private Integer taskId;

    public SynchronizedData() {
    }

    public SynchronizedData(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId ) {
        this.postOffset = postOffset;
        this.postVkId = postVkId;
        this.wallId = wallId;
        this.taskId = taskId;
    }
    public SynchronizedData(Task task, Wall wall, Integer postOffset, Integer postVkId ) {
        this(task.getId(),wall.getId(), postOffset,postVkId);
    }
    public SynchronizedData(Task task, Wall wall, com.epam.lab.spider.model.vk.Post vkPost, Integer postOffset) {
        this(task.getId(),wall.getId(), postOffset,vkPost.getId());
    }
    public SynchronizedData(Task task, Wall wall, PostOffsetDecorator postDecorated) {
        this(task.getId(),wall.getId(), postDecorated.getOffset(),postDecorated.getId());
    }

    public Integer getPostOffset() {
        return postOffset;
    }

    public void setPostOffset(Integer postOffset) {
        this.postOffset = postOffset;
    }

    public Integer getPostVkId() {
        return postVkId;
    }

    public void setPostVkId(Integer postVkId) {
        this.postVkId = postVkId;
    }

    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
