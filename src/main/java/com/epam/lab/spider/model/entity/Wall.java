package com.epam.lab.spider.model.entity;


import com.epam.lab.spider.model.PersistenceIdentifiable;

/**
 * @author Yura Kovalik
 */
public interface Wall extends PersistenceIdentifiable {
    Owner getOwner();

    void setOwner(Owner owner);

    Profile getProfile();

    void setProfile(Profile profile);

    Integer getId();

    Integer getOwnerId();

    void setOwnerId(Integer owner_id);

    Integer getProfileId();

    void setProfileId(Integer profile_id);

    Permission getPermission();

    void setPermission(Permission permission);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    enum Permission {
        READ, WRITE
    }
}
