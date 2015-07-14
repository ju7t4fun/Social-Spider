package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.vk.*;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class SynchronizedDataImpl implements SynchronizedData {

    private Integer postOffset;
    private Integer postVkId;
    private Integer wallId;
    private Integer taskId;

    public SynchronizedDataImpl() {
    }

    public SynchronizedDataImpl(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId) {
        this.postOffset = postOffset;
        this.postVkId = postVkId;
        this.wallId = wallId;
        this.taskId = taskId;
    }
    public SynchronizedDataImpl(Task task, Wall wall, Integer postOffset, Integer postVkId) {
        this(task.getId(),wall.getId(), postOffset,postVkId);
    }
    public SynchronizedDataImpl(Task task, Wall wall, com.epam.lab.spider.model.vk.Post vkPost, Integer postOffset) {
        this(task.getId(),wall.getId(), postOffset,vkPost.getId());
    }
    public SynchronizedDataImpl(Task task, Wall wall, PostOffsetDecorator postDecorated) {
        this(task.getId(),wall.getId(), postDecorated.getOffset(),postDecorated.getId());
    }

    @Override
    public void syncWith(SynchronizedData otherSync) {
        this.postOffset = otherSync.getPostOffset();
        this.postVkId = otherSync.getPostVkId();
        this.wallId = otherSync.getWallId();
        this.taskId = otherSync.getTaskId();
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
