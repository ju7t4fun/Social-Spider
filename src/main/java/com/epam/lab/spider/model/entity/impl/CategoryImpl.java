package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Task;

import java.util.Set;

/**
 * @author Oleksandra Lobanok
 */
public class CategoryImpl implements Category, PersistenceIdentificationChangeable {

    private Integer id;
    private String name;
    private String imageUrl = "/img/categories/nophoto.png";
    private Set<Task> tasks = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryImpl category = (CategoryImpl) o;

        return !(id != null ? !id.equals(category.id) : category.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Set<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean addTask(Task task) {
        return getTasks().add(task);
    }

    @Override
    public boolean removeTask(Task task) {
        return getTasks().remove(task);
    }

    @Override
    public String toString() {
        return name.replace("\\|", "-");
    }

}
