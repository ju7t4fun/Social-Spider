package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.impl.CategoryImpl;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindCategoryImpl extends CategoryImpl {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static TaskService taskService = PersistenceBindCategoryImpl.factory.create(TaskService.class);

    @Override
    public Set<Task> getTasks() {
        if (super.getTasks() == null) {
            if (getId() == null)
                setTasks(new HashSet<Task>());
            else
                setTasks(new HashSet<>(taskService.getByCategoryId(getId())));
        }
        return super.getTasks();
    }
}
