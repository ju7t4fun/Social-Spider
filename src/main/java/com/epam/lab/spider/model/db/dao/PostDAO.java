package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.Post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public interface PostDAO extends CRUD<Post>, SavableDAO<Post> {

    List<Post> getAllNotInNewPost(Connection connection) throws SQLException;

    List<Post> getByUserId(Connection connection, Integer userId) throws SQLException;

    List<Post> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getCountByUserId(Connection connection, Integer id) throws SQLException;

    List<Post> getByUserIdWithSearch(Connection conn, Integer id, int page, int size, String q) throws SQLException;

    int getCountByUserIdWithSearch(Connection connection, Integer id, String messageToSearch) throws SQLException;

    List<Post> getByCategoryId(Connection connection, int categoryId, int offset, int limit) throws SQLException;

    List<Integer> getByCategoryFromUser(Connection connection, int userId, int offset, int limit) throws SQLException;

}
