package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.entity.impl.*;

/**
 * @author Yura Kovalik
 */
public class PersistenceEntityFactory implements AbstractEntityFactory {

    private static AbstractEntityFactory instance = new PersistenceEntityFactory();

    private PersistenceEntityFactory() {
    }

    public static AbstractEntityFactory getInstance() {
        return instance;
    }

    @Override
    public Task createTask() {
        return new PersistenceBindTaskImp();
    }

    @Override
    public User createUser() {
        return new PersistenceBindUserImpl();
    }

    @Override
    public UserActions createUserActions() {
        return new UserActionsImpl();
    }

    @Override
    public Wall createWall() {
        return new PersistenceBindWallImpl();
    }

    @Override
    public Attachment createAttachment() {
        return new AttachmentImpl();
    }

    @Override
    public Category createCategory() {
        return new PersistenceBindCategoryImpl();
    }

    @Override
    public Event createEvent() {
        return BasicEntityFactory.getSynchronized().createEvent();
    }

    @Override
    public Filter createFilter() {
        return BasicEntityFactory.getSynchronized().createFilter();
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
        return new PersistenceBindPostImpl();
    }

    @Override
    public PostingTask createPostingTask() {
        return new PersistenceBindPostingTaskImpl();
    }

    @Override
    public Profile createProfile() {
        return new ProfileImpl();
    }
}
