package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Owner;

/**
 * @author Marian Voronovskyi
 */
public class OwnerImpl implements Owner, PersistenceIdentificationChangeable {

    private Integer id;
    private Integer vkId;
    private String name;
    private String domain;
    private Boolean deleted = false;
    private Integer userId;
    private Boolean banned = false;

    @Override
    public Boolean getBanned() {
        return banned;
    }

    @Override
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public Integer getVkId() {
        return vkId;
    }

    @Override
    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
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
        return "OwnerImpl{" +
                "id=" + id +
                ", vk_id=" + vkId +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", deleted=" + deleted +
                ", userId=" + userId +
                '}';
    }
}
