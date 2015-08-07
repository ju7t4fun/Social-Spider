package com.epam.lab.spider.model.entity;

import com.epam.lab.spider.model.PersistenceIdentifiable;

import java.util.Date;

/**
 * @author Yura Kovalik
 */
public interface PostingTask extends PersistenceIdentifiable {
    String getFullId();

    Integer getId();

    Integer getPostId();

    void setPostId(Integer postId);

    Integer getWallId();

    void setWallId(Integer wallId);

    Date getPostTime();

    void setPostTime(Date postTime);

    Date getDeleteTime();

    void setDeleteTime(Date deleteTime);

    State getState();

    void setState(State state);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

    Integer getUserId();

    void setUserId(Integer userId);

    Integer getVkPostId();

    void setVkPostId(Integer vkPostId);

    Stats getStats();

    void setStats(Stats stats);

    Post getPost();

    void setPost(Post post);

    com.epam.lab.spider.integration.vk.api.Stats.Reach getPostReach();

    Owner getOwner();

    void setOwner(Owner owner);

    Profile getProfile();

    void setProfile(Profile profile);

    enum State {
        CREATED, POSTING, POSTED, DELETED, ERROR, RESTORED
    }

    interface Stats {

        int getLikes();

        int getRePosts();

        int getComments();

    }
}
