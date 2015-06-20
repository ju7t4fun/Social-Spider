package com.epam.lab.spider.model.db.entity;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Attachment {

    private Integer id;
    private String payload;
    private Integer postId;
    private Post post;
    private Type type;
    private Boolean deleted = false;
    private Mode mode = Mode.URL;

    public enum Type {
        AUDIO, PHOTO, VIDEO, DOC
    }
    public enum Mode{
        URL,CODE
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getPostId() {
        if(post != null){
            return post.getId();
        }
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                ", postId=" + postId +
                ", type=" + type +
                ", deleted=" + deleted +
                '}';
    }
}
