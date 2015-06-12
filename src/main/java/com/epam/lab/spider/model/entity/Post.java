package com.epam.lab.spider.model.entity;

/**
 * Created by Sasha on 12.06.2015.
 */
public class Post {

    private Integer id;
    private String message;

    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id;}

    public String getMessage() { return message;}

    public void setMessage(String message) {this.message = message;}

    @Override
    public String toString() {
        return "Post { id=" + id +
                ", message " + message + " }";
    }
}
