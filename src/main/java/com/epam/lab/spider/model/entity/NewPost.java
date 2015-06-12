package com.epam.lab.spider.model.entity;

import java.util.Date;

/**
 * Created by Sasha on 12.06.2015.
 */
public class NewPost {

    private Integer id;
    private Integer postId;
    private Integer wallId;
    private Date postTime;
    private Date deleteTime;
    private Integer metadateId;

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

    public Integer getMetadateId() {
        return metadateId;
    }

    public void setMetadateId(Integer metadateId) {
        this.metadateId = metadateId;
    }

    @Override
    public String toString() {
        return "New Post {" +
                "id="+ id +
                " postId=" + postId +
                " wallId=" + wallId +
                " postTime=" + postTime +
                " deleteTime=" + deleteTime +
                " metadateId=" + metadateId + "}";
    }
}
