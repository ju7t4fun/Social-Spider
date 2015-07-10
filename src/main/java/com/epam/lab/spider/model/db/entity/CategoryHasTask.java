package com.epam.lab.spider.model.db.entity;

/**
 * Created by Орест on 7/10/2015.
 */
public class CategoryHasTask {

    Integer categoryId;
    Integer taskId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public CategoryHasTask(){}

    public CategoryHasTask(Integer categoryId, Integer taskId) {
        this.categoryId = categoryId;
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "CategoryHasTask{" +
                "categoryId=" + categoryId +
                ", taskId=" + taskId +
                '}';
    }
}
