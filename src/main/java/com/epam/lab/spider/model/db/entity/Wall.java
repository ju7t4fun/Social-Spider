package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.db.service.OwnerService;
import com.epam.lab.spider.model.db.service.ProfileService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class Wall {

    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final OwnerService ownerService = factory.create(OwnerService.class);
    private static final ProfileService profileService = factory.create(ProfileService.class);

    private Integer id;
    private Integer owner_id;
    private Integer profile_id;
    private Permission permission;
    private Boolean deleted;

    private Owner owner;
    private Profile profile;



    public enum Permission {
        READ, WRITE
    }

    public Owner getOwner() {
        if (owner == null) {
            if (owner_id == null)
                owner = new Owner();
            else
                owner = ownerService.getById(owner_id);
        }
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Profile getProfile() {
        if (profile == null) {
            if (profile_id == null)
                profile = new Profile();
            else
                profile = profileService.getById(profile_id);
        }
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wall wall = (Wall) o;

        if (id != null ? !id.equals(wall.id) : wall.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
