package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.Post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public interface PostDAO extends CRUD<Post>,SavableDAO<Post> {

    List<Post> getAllNotInNewPost(Connection connection) throws SQLException;

    List<Post> getByUserId(Connection connection, Integer userId) throws SQLException;
}
