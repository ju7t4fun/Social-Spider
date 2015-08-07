package com.epam.lab.spider.model.entity.sync;

import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.Profile;

import java.util.Date;

/**
 * @author Yura Kovalik
 *         TODO: CHECK arg and field equals in setters
 */
public class PostingTaskSynchronized extends EntitySynchronized<PostingTask> implements PostingTask {
    public PostingTaskSynchronized(PostingTask entity) {
        super(entity);
    }

    @Override
    public String toString() {
        return getEntity().toString();
    }

    public String getFullId() {
        return getEntity().getFullId();
    }

    public Integer getId() {
        return getEntity().getId();
    }

    public Integer getPostId() {
        return getEntity().getPostId();
    }

    public void setPostId(Integer postId) {
        setUnSynchronized();
        getEntity().setPostId(postId);
    }

    public Integer getWallId() {
        return getEntity().getWallId();
    }

    public void setWallId(Integer wallId) {
        setUnSynchronized();
        getEntity().setWallId(wallId);
    }

    public Date getPostTime() {
        return getEntity().getPostTime();
    }

    public void setPostTime(Date postTime) {
        setUnSynchronized();
        getEntity().setPostTime(postTime);
    }

    public Date getDeleteTime() {
        return getEntity().getDeleteTime();
    }

    public void setDeleteTime(Date deleteTime) {
        setUnSynchronized();
        getEntity().setDeleteTime(deleteTime);
    }

    public State getState() {
        return getEntity().getState();
    }

    public void setState(State state) {
        setUnSynchronized();
        getEntity().setState(state);
    }

    public Boolean getDeleted() {
        return getEntity().getDeleted();
    }

    public void setDeleted(Boolean deleted) {
        setUnSynchronized();
        getEntity().setDeleted(deleted);
    }

    public Integer getUserId() {
        return getEntity().getUserId();
    }

    public void setUserId(Integer userId) {
        setUnSynchronized();
        getEntity().setUserId(userId);
    }

    public Integer getVkPostId() {
        return getEntity().getVkPostId();
    }

    public void setVkPostId(Integer vkPostId) {
        setUnSynchronized();
        getEntity().setVkPostId(vkPostId);
    }

    public Stats getStats() {
        return getEntity().getStats();
    }

    public void setStats(Stats stats) {
        setUnSynchronized();
        getEntity().setStats(stats);
    }

    public Post getPost() {
        return getEntity().getPost();
    }

    public void setPost(Post post) {
        setUnSynchronized();
        getEntity().setPost(post);
    }

    public com.epam.lab.spider.integration.vk.api.Stats.Reach getPostReach() {
        return getEntity().getPostReach();
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
}
