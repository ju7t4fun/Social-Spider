package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.impl.OwnerImpl;
import com.epam.lab.spider.model.entity.impl.ProfileImpl;
import com.epam.lab.spider.model.entity.impl.WallImpl;
import com.epam.lab.spider.persistence.service.OwnerService;
import com.epam.lab.spider.persistence.service.ProfileService;
import com.epam.lab.spider.persistence.service.ServiceFactory;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindWallImpl extends WallImpl {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static OwnerService ownerService = PersistenceBindWallImpl.factory.create(OwnerService.class);
    private static ProfileService profileService = PersistenceBindWallImpl.factory.create(ProfileService.class);

    @Override
    public Owner getOwner() {
        if (super.getOwner() == null) {
            if (getOwnerId() == null)
                setOwner(new OwnerImpl());
            else
                setOwner(ownerService.getById(getOwnerId()));
        }
        return super.getOwner();
    }

    @Override
    public Profile getProfile() {
        if (super.getProfile() == null) {
            if (getProfileId() == null)
                setProfile(new ProfileImpl());
            else
                setProfile(profileService.getById(getProfileId()));
        }
        return super.getProfile();
    }
}
