package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.SocialNetworkUtils;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Wall;

import java.util.Date;
import java.util.Set;

/**
 * @author Boyarsky Vitaliy
 */
public class ProfileImpl implements Profile, PersistenceIdentificationChangeable {

    private Integer id;
    private Integer userId;
    private Integer vkId;
    private String accessToken;
    private Date extTime;
    private Integer appId = SocialNetworkUtils.getDefaultVkAppsIdAsApps();
    private String name = "NO NAME";
    private Boolean deleted = false;

    private Set<Wall> walls = null;
    private AccessToken vkAccessToken = null;

    @Override public boolean isExpired() {
        try {
            if (new Date().getTime() < extTime.getTime())
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override public Integer getId() {
        return id;
    }

    @Override public void setId(Integer id) {
        this.id = id;
    }

    @Override public Integer getUserId() {
        return userId;
    }

    @Override public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override public Integer getVkId() {
        return vkId;
    }

    @Override public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    @Override public String getAccessToken() {
        return accessToken;
    }

    @Override public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override public Date getExtTime() {
        return extTime;
    }

    @Override public void setExtTime(Date extTime) {
        this.extTime = extTime;
    }

    @Override public Integer getAppId() {
        return appId;
    }

    @Override public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override public Boolean getDeleted() {
        return deleted;
    }

    @Override public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override public String getName() {
        return name;
    }

    @Override public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", vkId=" + vkId +
                ", accessToken='" + accessToken + '\'' +
                ", extTime=" + extTime +
                ", appId=" + appId +
                ", deleted=" + deleted +
                '}';
    }

}
