package com.epam.lab.spider.model.entity;

import java.util.Date;

/**
 * Created by Sasha on 12.06.2015.
 */
public class NewPost {

    private Integer id;
    private Post post;
    private Integer wallId;
    private Date postTime;
    private Date deleteTime;
    private PostMetadata metadata;
    private State state = State.CREATED;
    private Boolean deleted = false;

    public enum State {

        CREATED(1), POSTED(2), DELETED(3);

        private int id;

        State(int id) {
            this.id = id;
        }

        public static State getById(int id) {
            State[] states = values();
            for (State state : states) {
                if (state.id == id)
                    return state;
            }
            return null;
        }

        public int getId() {
            return id;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public PostMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PostMetadata metadata) {
        this.metadata = metadata;
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

    @Override
    public String toString() {
        return "NewPost{" +
                "id=" + id +
                ", post=" + post +
                ", wallId=" + wallId +
                ", postTime=" + postTime +
                ", deleteTime=" + deleteTime +
                ", metadata=" + metadata +
                ", state=" + state +
                ", deleted=" + deleted +
                '}';
    }

}
