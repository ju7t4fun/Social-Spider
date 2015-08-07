package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Profile;

import java.util.Date;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class ProfileSynchronized extends EntitySynchronized<Profile> implements Profile {
    public ProfileSynchronized(Profile entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public boolean isExpired() {
        return getEntity().isExpired();
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public Integer getVkId() {
        return getEntity().getVkId();
    }

    public void setVkId(Integer vkId) {
        setUnSynchronized();
        getEntity().setVkId(vkId);
    }

    public String getAccessToken() {
        return getEntity().getAccessToken();
    }

    public void setAccessToken(String accessToken) {
        setUnSynchronized();
        getEntity().setAccessToken(accessToken);
    }

    public Date getExtTime() {
        return getEntity().getExtTime();
    }

    public void setExtTime(Date extTime) {
        setUnSynchronized();
        getEntity().setExtTime(extTime);
    }

    public Integer getAppId() {
        return getEntity().getAppId();
    }

    public void setAppId(Integer appId) {
        setUnSynchronized();
        getEntity().setAppId(appId);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public String getName() {
        return getEntity().getName();
    }

    public void setName(String name) {
        setUnSynchronized();
        getEntity().setName(name);
    }
}
