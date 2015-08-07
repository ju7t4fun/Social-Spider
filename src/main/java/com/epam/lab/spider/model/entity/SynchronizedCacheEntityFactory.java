package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.EntitySynchronizedCacheWrapperUtil;

/**
 * @author Yura Kovalik
 */
public class SynchronizedCacheEntityFactory implements AbstractEntityFactory {
    private AbstractEntityFactory basicEntityFactory;

    @Override
    public Attachment createAttachment() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createAttachment());
    }

    @Override
    public Category createCategory() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createCategory());
    }

    @Override
    public Event createEvent() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createEvent());
    }

    @Override
    public Filter createFilter() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createFilter());
    }

    @Override
    public Message createMessage() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createMessage());
    }

    @Override
    public Owner createOwner() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createOwner());
    }

    @Override
    public Post createPost() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createPost());
    }

    @Override
    public PostingTask createPostingTask() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createPostingTask());
    }

    @Override
    public Profile createProfile() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createProfile());
    }

    @Override
    public Task createTask() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createTask());
    }

    @Override
    public User createUser() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createUser());
    }

    @Override
    public UserActions createUserActions() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createUserActions());
    }

    @Override
    public Wall createWall() {
        return EntitySynchronizedCacheWrapperUtil.wrap(basicEntityFactory.createWall());
    }

    public SynchronizedCacheEntityFactory(AbstractEntityFactory basicEntityFactory) {
        this.basicEntityFactory = basicEntityFactory;
    }

}
