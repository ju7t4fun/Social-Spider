package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Set;

/**
 * @author Yura Kovalik
 */
public interface Category extends PersistenceIdentifiable {

    Integer getId();

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    Set<Task> getTasks();

    void setTasks(Set<Task> tasks);

    boolean addTask(Task task);

    boolean removeTask(Task task);
}
