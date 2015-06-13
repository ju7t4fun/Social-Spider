package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.service.ServiceFactory;
import com.epam.lab.spider.model.service.WallService;

import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Task {

    private Integer id;
    private Integer userId;
    private Filter filter;
    private Type type;
    private List<Wall> destination = null;
    private List<Wall> source = null;

    public enum Type {
        COPY, REPOST, FAVORITE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Wall> getDestination() {
        if (destination == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            WallService service = factory.create(WallService.class);
            destination = service.getDestinationByTaskId(id);
        }
        return destination;
    }

    public void setDestination(List<Wall> destination) {
        this.destination = destination;
    }

    public List<Wall> getSource() {
        if (source == null) {
            ServiceFactory factory = ServiceFactory.getInstance();
            WallService service = factory.create(WallService.class);
            source = service.getSourceByTaskId(id);
        }
        return source;
    }

    public void setSource(List<Wall> source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId=" + userId +
                ", filter=" + filter +
                ", type=" + type +
                ", destination=" + destination +
                ", source=" + source +
                '}';
    }
}
