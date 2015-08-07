package com.epam.lab.spider.model.entity;

/**
 * @author Yura Kovalik
 */
public interface AbstractEntityFactory {
    Attachment createAttachment();

    Category createCategory();

    Event createEvent();

    Filter createFilter();

    Message createMessage();

    Owner createOwner();

    Post createPost();

    PostingTask createPostingTask();

    Profile createProfile();

    Task createTask();

    User createUser();

    UserActions createUserActions();

    Wall createWall();
}
