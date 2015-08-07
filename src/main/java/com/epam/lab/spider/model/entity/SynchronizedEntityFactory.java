package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.SynchronizedWrapperUtils;

/**
 * @author Yura Kovalik
 */
public class SynchronizedEntityFactory implements AbstractEntityFactory {
    private AbstractEntityFactory basicEntityFactory;

    @Override
    public Attachment createAttachment() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createAttachment());
    }

    @Override
    public Category createCategory() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createCategory());
    }

    @Override
    public Event createEvent() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createEvent());
    }

    @Override
    public Filter createFilter() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createFilter());
    }

    @Override
    public Message createMessage() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createMessage());
    }

    @Override
    public Owner createOwner() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createOwner());
    }

    @Override
    public Post createPost() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createPost());
    }

    @Override
    public PostingTask createPostingTask() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createPostingTask());
    }

    @Override
    public Profile createProfile() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createProfile());
    }

    @Override
    public Task createTask() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createTask());
    }

    @Override
    public User createUser() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createUser());
    }

    @Override
    public UserActions createUserActions() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createUserActions());
    }

    @Override
    public Wall createWall() {
        return SynchronizedWrapperUtils.wrap(basicEntityFactory.createWall());
    }

    public SynchronizedEntityFactory(AbstractEntityFactory basicEntityFactory) {
        this.basicEntityFactory = basicEntityFactory;
    }

}
