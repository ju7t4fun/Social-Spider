package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Wall;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class WallSynchronized extends EntitySynchronized<Wall> implements Wall {
    public WallSynchronized(Wall entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public Owner getOwner() {
        return getEntity().getOwner();
    }

    public void setOwner(Owner owner) {
        setUnSynchronized();
        getEntity().setOwner(owner);
    }

    public Profile getProfile() {
        return getEntity().getProfile();
    }

    public void setProfile(Profile profile) {
        setUnSynchronized();
        getEntity().setProfile(profile);
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getOwnerId() {
        return getEntity().getOwnerId();
    }

    public void setOwnerId(Integer owner_id) {
        setUnSynchronized();
        getEntity().setOwnerId(owner_id);
    }

    public Integer getProfileId() {
        return getEntity().getProfileId();
    }

    public void setProfileId(Integer profile_id) {
        setUnSynchronized();
        getEntity().setProfileId(profile_id);
    }

    public Permission getPermission() {
        return getEntity().getPermission();
    }

    public void setPermission(Permission permission) {
        setUnSynchronized();
        getEntity().setPermission(permission);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }
}
