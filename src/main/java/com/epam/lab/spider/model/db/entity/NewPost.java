package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.db.service.PostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;

import java.util.Date;

/**
 * Created by Sasha on 12.06.2015.
 */
public class NewPost {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static PostService service = factory.create(PostService.class);

    private Integer id;
    private Integer postId;
    private Integer wallId;
    private Date postTime;
    private Date deleteTime;
    private State state = State.CREATED;
    private Boolean deleted = false;

    private Post post;

    public enum State {
        CREATED, POSTING, POSTED, DELETED, ERROR, RESTORED
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Post getPost() {
        if (post == null) {
            if (postId == null)
                post = new Post();
            else
                post = service.getById(postId);
        }
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "NewPost{" +
                "id=" + id +
                ", postId=" + postId +
                ", wallId=" + wallId +
                ", postTime=" + postTime +
                ", deleteTime=" + deleteTime +
                ", state=" + state +
                ", deleted=" + deleted +
                ", post=" + post +
                '}';
    }

}
