package com.epam.lab.spider.persistence.factory;

import com.epam.lab.spider.model.entity.SynchronizedDataAuditable;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.entity.impl.SynchronizedDataAuditableImpl;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;
import com.epam.lab.spider.persistence.dao.TaskSynchronizedInfoDAO;
import com.epam.lab.spider.persistence.dao.mysql.TaskSynchronizedInfoAuditableDAOImpl;

/**
 * @author Yura Kovalik
 */
public class SynchronizedDataAuditableFactoryImpl extends SynchronizedDataAbstractFactory<SynchronizedDataAuditable> {
    private static final TaskSynchronizedInfoDAO dao = new TaskSynchronizedInfoAuditableDAOImpl();
    public TaskSynchronizedInfoDAO createTaskSynchronizedNewDataDAO() {
        return dao;
    }
    public SynchronizedDataAuditable createSynchronizedData() {
        return new SynchronizedDataAuditableImpl();
    }

    @Override
    public SynchronizedDataAuditable createSynchronizedData(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId) {
        return new SynchronizedDataAuditableImpl(taskId,wallId,postOffset,postVkId);
    }

    @Override
    public SynchronizedDataAuditable createSynchronizedData(Task task, Wall wall, Integer postOffset, Integer postVkId) {
        return new SynchronizedDataAuditableImpl(task,wall,postOffset,postVkId);
    }

    @Override
    public SynchronizedDataAuditable createSynchronizedData(Task task, Wall wall, Post vkPost, Integer postOffset) {
        return new SynchronizedDataAuditableImpl(task,wall,vkPost,postOffset);
    }

    @Override
    public SynchronizedDataAuditable createSynchronizedData(Task task, Wall wall, PostOffsetDecorator postDecorated) {
        return new SynchronizedDataAuditableImpl(task,wall,postDecorated);
    }

}
