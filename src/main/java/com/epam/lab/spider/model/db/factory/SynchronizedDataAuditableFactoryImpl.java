package com.epam.lab.spider.model.db.factory;

import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.dao.mysql.TaskSynchronizedNewDataAuditableDAOImpl;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;

/**
 * Created by hell-engine on 7/12/2015.
 */
public class SynchronizedDataAuditableFactoryImpl extends SynchronizedDataAbstractFactory<SynchronizedDataAuditable> {
    private static final TaskSynchronizedNewDataDAO dao = new TaskSynchronizedNewDataAuditableDAOImpl();
    public TaskSynchronizedNewDataDAO createTaskSynchronizedNewDataDAO() {
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
