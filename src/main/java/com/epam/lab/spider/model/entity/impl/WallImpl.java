package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Wall;

/**
 * @author Dzyuba Orest
 * @author Yura Kovalik
 */
public class WallImpl implements Wall, PersistenceIdentificationChangeable {

    private Integer id;
    private Integer ownerId;
    private Integer profileId;
    private Permission permission;
    private Boolean deleted;

    private Owner owner;
    private Profile profile;


    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;
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
    public Integer getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public Integer getProfileId() {
        return profileId;
    }

    @Override
    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Override
    public void setPermission(Permission permission) {
        this.permission = permission;
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
        return "Wall{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", profileId=" + profileId +
                ", permission=" + permission +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wall wall = (Wall) o;

        return !(id != null ? !id.equals(wall.getId()) : wall.getId() != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
