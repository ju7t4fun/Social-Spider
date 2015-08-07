package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Owner;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class OwnerSynchronized extends EntitySynchronized<Owner> implements Owner {
    public OwnerSynchronized(Owner entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Boolean getBanned() {
        return getEntity().getBanned();
    }

    public void setBanned(Boolean banned) {
        setUnSynchronized();
        getEntity().setBanned(banned);
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getVkId() {
        return getEntity().getVkId();
    }

    public void setVkId(Integer vkId) {
        setUnSynchronized();
        getEntity().setVkId(vkId);
    }

    public String getName() {
        return getEntity().getName();
    }

    public void setName(String name) {
        setUnSynchronized();
        getEntity().setName(name);
    }

    public String getDomain() {
        return getEntity().getDomain();
    }

    public void setDomain(String domain) {
        setUnSynchronized();
        getEntity().setDomain(domain);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }
}
