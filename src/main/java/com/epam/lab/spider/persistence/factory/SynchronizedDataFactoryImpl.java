package com.epam.lab.spider.persistence.factory;

import com.epam.lab.spider.model.entity.SynchronizedData;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.entity.impl.SynchronizedDataImpl;
import com.epam.lab.spider.model.vk.Post;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;
import com.epam.lab.spider.persistence.dao.TaskSynchronizedInfoDAO;
import com.epam.lab.spider.persistence.dao.mysql.TaskSynchronizedInfoDAOImpl;

/**
 * @author Yura Kovalik
 */
public class SynchronizedDataFactoryImpl extends SynchronizedDataAbstractFactory<SynchronizedData> {
    private static final TaskSynchronizedInfoDAO dao = new TaskSynchronizedInfoDAOImpl();
    public TaskSynchronizedInfoDAO createTaskSynchronizedNewDataDAO() {
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
