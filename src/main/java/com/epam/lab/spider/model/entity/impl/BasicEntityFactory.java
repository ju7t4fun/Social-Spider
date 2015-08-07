package com.epam.lab.spider.model.entity.impl;

import com.epam.lab.spider.model.entity.*;

/**
 * @author Yura Kovalik
 */
public class BasicEntityFactory implements AbstractEntityFactory {

    private static AbstractEntityFactory instance = new BasicEntityFactory();
    private static AbstractEntityFactory synchronizedCacheVersion = new SynchronizedCacheEntityFactory(instance);

    private BasicEntityFactory() {
    }

    public static AbstractEntityFactory getSynchronized() {
        return synchronizedCacheVersion;
    }

    public static AbstractEntityFactory getInstance() {
        return instance;
    }

    @Override
    public Task createTask() {
        return new TaskImpl();
    }

    @Override
    public User createUser() {
        return new UserImpl();
    }

    @Override
    public UserActions createUserActions() {
        return new UserActionsImpl();
    }

    @Override
    public Wall createWall() {
        return new WallImpl();
    }

    @Override
    public Attachment createAttachment() {
        return new AttachmentImpl();
    }

    @Override
    public Category createCategory() {
        return new CategoryImpl();
    }

    @Override
    public Event createEvent() {
        return new EventImpl();
    }

    @Override
    public Filter createFilter() {
        return new FilterImpl();
    }

    @Override
    public Message createMessage() {
        return new MessageImpl();
    }

    @Override
    public Owner createOwner() {
        return new OwnerImpl();
    }

    @Override
    public Post createPost() {
        return new PostImpl();
    }

    @Override
    public PostingTask createPostingTask() {
        return new PostingTaskImpl();
    }

    @Override
    public Profile createProfile() {
        return new ProfileImpl();
    }
}
