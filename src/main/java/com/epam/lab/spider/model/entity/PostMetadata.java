package com.epam.lab.spider.model.entity;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class PostMetadata {
    private Integer id;
    private Integer like;
    private Integer repost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getRepost() {
        return repost;
    }

    public void setRepost(Integer repost) {
        this.repost = repost;
    }

    @Override
    public String toString() {
        return "PostMetadata{" +
                "id=" + id +
                ", like=" + like +
                ", repost=" + repost +
                '}';
    }
}
