package com.epam.lab.spider.model.db.factory;

import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.dao.mysql.TaskSynchronizedNewDataAuditableDAOImpl;
import com.epam.lab.spider.model.db.dao.mysql.TaskSynchronizedNewDataDAOImpl;
import com.epam.lab.spider.model.db.entity.SynchronizedData;
import com.epam.lab.spider.model.db.entity.SynchronizedDataImpl;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;

/**
 * Created by hell-engine on 7/12/2015.
 */
public class SynchronizedDataFactoryImpl extends SynchronizedDataAbstractFactory<SynchronizedData> {
    private static final TaskSynchronizedNewDataDAO dao = new TaskSynchronizedNewDataDAOImpl();
    public TaskSynchronizedNewDataDAO createTaskSynchronizedNewDataDAO() {
        return dao;
    }
    public SynchronizedData createSynchronizedData() {
        return new SynchronizedDataImpl();
    }

    @Override
    public SynchronizedData createSynchronizedData(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId) {
        return new SynchronizedDataImpl(taskId,wallId,postOffset,postVkId);
    }

    @Override
    public SynchronizedData createSynchronizedData(Task task, Wall wall, Integer postOffset, Integer postVkId) {
        return new SynchronizedDataImpl(task,wall,postOffset,postVkId);
    }

    @Override
    public SynchronizedData createSynchronizedData(Task task, Wall wall, Post vkPost, Integer postOffset) {
        return new SynchronizedDataImpl(task,wall,vkPost,postOffset);
    }

    @Override
    public SynchronizedData createSynchronizedData(Task task, Wall wall, PostOffsetDecorator postDecorated) {
        return new SynchronizedDataImpl(task,wall,postDecorated);
    }

}
