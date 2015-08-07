package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Filter;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class FilterSynchronized extends EntitySynchronized<Filter> implements Filter {
    public FilterSynchronized(Filter entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Integer getId() {

        return getEntity().getId();
    }

    public Integer getLikes() {
        return getEntity().getLikes();
    }

    public void setLikes(Integer likes) {
        setUnSynchronized();
        getEntity().setLikes(likes);
    }

    public Integer getRePosts() {
        return getEntity().getRePosts();
    }

    public void setRePosts(Integer reposts) {
        setUnSynchronized();
        getEntity().setRePosts(reposts);
    }

    public Integer getComments() {
        return getEntity().getComments();
    }

    public void setComments(Integer comments) {
        setUnSynchronized();
        getEntity().setComments(comments);
    }

    public Long getMinTime() {
        return getEntity().getMinTime();
    }

    public void setMinTime(Long minTime) {
        setUnSynchronized();
        getEntity().setMinTime(minTime);
    }

    public Long getMaxTime() {
        return getEntity().getMaxTime();
    }

    public void setMaxTime(Long maxTime) {
        setUnSynchronized();
        getEntity().setMaxTime(maxTime);
    }

    public Boolean getDeleted() {
        setUnSynchronized();
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        getEntity().setDeleted(deleted);
    }
}
