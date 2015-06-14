package com.epam.lab.spider.model.entity;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class Wall {

    private Integer id;
    private Integer owner_id;
    private Integer profile_id;
    private Permission permission;
    private Boolean deleted;

    public enum Permission {
        READ, WRITE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", profile_id=" + profile_id +
                ", permission=" + permission +
                ", deleted=" + deleted +
                '}';
    }

}
