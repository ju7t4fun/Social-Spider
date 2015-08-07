package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Filter;

/**
 * @author Marian Voronovskyi
 */
public class FilterImpl implements Filter, PersistenceIdentificationChangeable {
    private Integer id;
    private Integer likes;
    private Integer rePosts;
    private Integer comments;
    private Long minTime;
    private Long maxTime;
    private Boolean deleted = false;

    protected FilterImpl() {
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
    public Integer getLikes() {
        return likes;
    }

    @Override
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public Integer getRePosts() {
        return rePosts;
    }

    @Override
    public void setRePosts(Integer rePosts) {
        this.rePosts = rePosts;
    }

    @Override
    public Integer getComments() {
        return comments;
    }

    @Override
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Override
    public Long getMinTime() {
        return minTime;
    }

    @Override
    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    @Override
    public Long getMaxTime() {
        return maxTime;
    }

    @Override
    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "id=" + id +
                ", likes=" + likes +
                ", rePosts=" + rePosts +
                ", comments=" + comments +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                ", deleted=" + deleted +
                '}';
    }
}
