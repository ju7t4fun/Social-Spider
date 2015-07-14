package com.epam.lab.spider.model.db.entity;

/**
 * Created by Орест on 7/5/2015.
 */
public class UserHasCategory {

    Integer userId;
    Integer categoryID;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public UserHasCategory() {}
    public  UserHasCategory(int userId, int categoryID) {
        this.userId = userId;
        this.categoryID = categoryID;
    }
    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "UserHasCategory{" +
                "userId=" + userId +
                ", categoryID=" + categoryID +
                '}';
    }
}
