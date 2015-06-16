package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.TaskService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Category {

    private static final ServiceFactory factory = ServiceFactory.getInstance();

    private Integer id;
    private String name;
    private Set<Task> tasks = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        if (tasks == null) {
            if (id == null)
                return new HashSet<>();
            TaskService service = factory.create(TaskService.class);
            tasks = new HashSet<>(service.getByCategoryId(id));
        }
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean addTask(Task task) {
        if (tasks == null) {
            tasks = getTasks();
        }
        return tasks.add(task);
    }

    public boolean removeTask(Task task) {
        if (tasks == null) {
            tasks = getTasks();
        }
        return tasks.remove(task);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }

}
