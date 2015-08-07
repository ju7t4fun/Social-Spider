package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.entity.SynchronizedData;
import com.epam.lab.spider.model.entity.SynchronizedDataAuditable;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;

/**
 * @author Yura Kovalik
 */
public class SynchronizedDataAuditableImpl implements SynchronizedDataAuditable {
    private final SynchronizedData synchronizedData;

    private Integer revisionCount = 0;

    @Override
    public Integer getRevisionCount() {
        return revisionCount;
    }

    @Override
    public void setRevisionCount(Integer revisionCount) {
        this.revisionCount = revisionCount;
    }

    public SynchronizedDataAuditableImpl() {
        synchronizedData = new SynchronizedDataImpl();
    }

    public SynchronizedDataAuditableImpl(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId) {
        synchronizedData = new SynchronizedDataImpl(taskId, wallId, postOffset, postVkId);
    }

    public SynchronizedDataAuditableImpl(Task task, Wall wall, Integer postOffset, Integer postVkId) {
        synchronizedData = new SynchronizedDataImpl(task, wall, postOffset, postVkId);
    }

    public SynchronizedDataAuditableImpl(Task task, Wall wall, com.epam.lab.spider.model.vk.Post vkPost, Integer postOffset) {
        synchronizedData = new SynchronizedDataImpl(task, wall, vkPost, postOffset);
    }

    public SynchronizedDataAuditableImpl(Task task, Wall wall, PostOffsetDecorator postDecorated) {
        synchronizedData = new SynchronizedDataImpl(task, wall, postDecorated);
    }

    @Override
    public void syncWith(SynchronizedData otherSync) {
        synchronizedData.syncWith(otherSync);
    }

    public Integer getPostOffset() {
        return synchronizedData.getPostOffset();
    }

    public void setPostOffset(Integer postOffset) {
        synchronizedData.setPostOffset(postOffset);
    }

    public Integer getPostVkId() {
        return synchronizedData.getPostVkId();
    }

    public void setPostVkId(Integer postVkId) {
        synchronizedData.setPostVkId(postVkId);
    }

    public Integer getWallId() {
        return synchronizedData.getWallId();
    }

    public void setWallId(Integer wallId) {
        synchronizedData.setWallId(wallId);
    }

    public Integer getTaskId() {
        return synchronizedData.getTaskId();
    }

    public void setTaskId(Integer taskId) {
        synchronizedData.setTaskId(taskId);
    }
}
