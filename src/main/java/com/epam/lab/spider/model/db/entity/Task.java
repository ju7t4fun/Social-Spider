package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.db.service.FilterService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Task {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final WallService service = factory.create(WallService.class);
    private static final FilterService filterService = factory.create(FilterService.class);

    private Integer id;
    private Integer userId;
    private Integer filterId;
    private Type type;
    private State state = State.STOPPED;
    private Boolean deleted = false;

    private Filter filter = null;
    private Set<Wall> destination = null;
    private Set<Wall> source = null;

    public enum Type {
        COPY, REPOST, FAVORITE
    }

    public enum State {
        RUNNING, STOPPED, ERROR
    }

    public Set<Wall> getDestination() {
        if (destination == null) {
            if (id == null)
                destination = new HashSet<>();
            else
                destination = new HashSet<>(service.getDestinationByTaskId(id));
        }
        return destination;
    }

    public boolean addDestination(Wall wall) {
        return getDestination().add(wall);
    }

    public boolean removeDestination(Wall wall) {
        return getDestination().remove(wall);
    }

    public Set<Wall> getSource() {
        if (source == null) {
            if (id == null)
                source = new HashSet<>();
            else
                source = new HashSet<>(service.getSourceByTaskId(id));
        }
        return source;
    }

    public boolean addSource(Wall wall) {
        return getSource().add(wall);
    }

    public boolean removeSource(Wall wall) {
        return getSource().remove(wall);
    }

    public Filter getFilter() {
        if (filter == null) {
            if (filterId == null)
                filter = new Filter();
            else
                filter = filterService.getById(filterId);
        }
        return filter;
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

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filter_id) {
        this.filterId = filter_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setDestination(Set<Wall> destination) {
        this.destination = destination;
    }

    public void setSource(Set<Wall> source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId=" + userId +
                ", filterId=" + filterId +
                ", type=" + type +
                ", state=" + state +
                ", deleted=" + deleted +
                ", filter=" + filter +
                ", destination=" + destination +
                ", source=" + source +
                '}';
    }

}
