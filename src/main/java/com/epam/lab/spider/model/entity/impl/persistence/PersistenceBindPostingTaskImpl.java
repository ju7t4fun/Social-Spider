package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.impl.PostImpl;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.WallService;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindPostingTaskImpl extends PostingTaskImpl {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = PersistenceBindPostingTaskImpl.factory.create(PostService.class);
    private static WallService wallService = PersistenceBindPostingTaskImpl.factory.create(WallService.class);

    @Override
    public Post getPost() {
        if (super.getPost() == null) {
            if (getPostId() == null)
                setPost(new PostImpl());
            else
                setPost(service.getById(getPostId()));
        }
        return super.getPost();
    }

    @Override
    public Owner getOwner() {
        if (super.getOwner() == null) {
            setOwner(wallService.getById(getWallId()).getOwner());
        }
        return super.getOwner();
    }

    @Override
    public Profile getProfile() {
        if (super.getProfile() == null) {
            setProfile(wallService.getById(getWallId()).getProfile());
        }
        return super.getProfile();
    }
}
