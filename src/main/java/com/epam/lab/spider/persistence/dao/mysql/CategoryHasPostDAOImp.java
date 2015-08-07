package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.persistence.dao.CategoryHasPostDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Boyarsky Vitaliy
 */
public class CategoryHasPostDAOImp extends BaseDAO implements CategoryHasPostDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO category_has_post (category_id, post_id) VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM category_has_post WHERE category_id = ? AND post_id";
    private static final String SQL_DELETE_BY_POST_ID_QUERY = "DELETE FROM category_has_post WHERE post_id = ?";
    private static final String SQL_DELETE_BY_CATEGORY_ID_QUERY = "DELETE FROM category_has_post WHERE category_id = ?";

    @Override
    public boolean insert(Connection connection, int categoryId, int postId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, categoryId, postId);
    }

    @Override
    public boolean delete(Connection connection, int categoryId, int postId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, categoryId, postId);
    }

    @Override
    public boolean deleteByPostId(Connection connection, int postId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_POST_ID_QUERY, postId);
    }

    @Override
    public boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_CATEGORY_ID_QUERY, categoryId);
    }

}
