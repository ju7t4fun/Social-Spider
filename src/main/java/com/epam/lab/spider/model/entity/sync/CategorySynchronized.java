package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Task;

import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class CategorySynchronized extends EntitySynchronized<Category> implements Category {
    public CategorySynchronized(Category entity) {
        super(entity);
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public String getName() {
        return getEntity().getName();
    }

    public void setName(String name) {
        if (getName().equals(name)) return;
        setUnSynchronized();
        getEntity().setName(name);
    }

    public String getImageUrl() {
        return getEntity().getImageUrl();
    }

    public void setImageUrl(String imageUrl) {
        if (getImageUrl().equals(imageUrl)) return;
        setUnSynchronized();
        getEntity().setImageUrl(imageUrl);
    }

    public Set<Task> getTasks() {
        return getEntity().getTasks();
    }

    public void setTasks(Set<Task> tasks) {
        if (getImageUrl().equals(tasks)) return;
        setUnSynchronized();
        getEntity().setTasks(tasks);
    }

    public boolean addTask(Task task) {
        if (getTasks().contains(task)) return false;
        setUnSynchronized();
        return getEntity().addTask(task);
    }


    public boolean removeTask(Task task) {
        if (!getTasks().contains(task)) return false;
        setUnSynchronized();
        return getEntity().removeTask(task);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }
}
