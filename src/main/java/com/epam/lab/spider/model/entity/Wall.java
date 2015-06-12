package com.epam.lab.spider.model.entity;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class Wall {
    private int id;
    private int owner_id;
    private int profile_id;
    private Permission permission;

    public enum Permission {
        READ, WRITE
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", profile_id=" + profile_id +
                ", permission=" + permission +
                '}';
    }
}
