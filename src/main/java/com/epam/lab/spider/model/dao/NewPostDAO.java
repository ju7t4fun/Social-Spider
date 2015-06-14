package com.epam.lab.spider.model.dao;

import com.epam.lab.spider.model.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public interface NewPostDAO  extends CRUD<NewPost> {
    public List<NewPost> getAllUnpostedByDate(Connection connection, Date date) throws SQLException;
}
