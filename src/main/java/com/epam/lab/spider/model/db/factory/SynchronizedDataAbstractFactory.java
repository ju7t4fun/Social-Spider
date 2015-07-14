package com.epam.lab.spider.model.db.factory;


import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.entity.SynchronizedData;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.vk.PostOffsetDecorator;

public abstract class SynchronizedDataAbstractFactory<T extends SynchronizedData> {

    public abstract T createSynchronizedData();

    public abstract T createSynchronizedData(Integer taskId, Integer wallId, Integer postOffset, Integer postVkId);

    public abstract T createSynchronizedData(Task task, Wall wall, Integer postOffset, Integer postVkId);

    public abstract T createSynchronizedData(Task task, Wall wall, com.epam.lab.spider.model.vk.Post vkPost, Integer postOffset);

    public abstract T createSynchronizedData(Task task, Wall wall, PostOffsetDecorator postDecorated);



    public abstract TaskSynchronizedNewDataDAO<T> createTaskSynchronizedNewDataDAO();
}