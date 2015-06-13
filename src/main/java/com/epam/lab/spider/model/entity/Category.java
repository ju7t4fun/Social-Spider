package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.TaskService;

import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Category {

    private Integer id;
    private String name;
    private List<Task> tasks = null;

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

    public List<Task> getTasks() {
        if (tasks == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            TaskService service = factory.create(TaskService.class);
            tasks = service.getByCategoryId(id);
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
