package com.epam.lab.spider.model.entity;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Task {
    private Integer id;
    private Integer userId;
    private Integer filterId;
    private Type type;

    public enum Type {
        COPY, REPOST, FAVORITE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "filterId=" + filterId +
                ", id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                '}';
    }
}
