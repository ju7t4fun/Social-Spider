package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.UserHasCategotyDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public class UserHasCategotyDAOImp extends BaseDAO implements UserHasCategotyDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO user_has_category (category_id, user_id) VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM user_has_category WHERE category_id = ? AND user_id";
    private static final String SQL_DELETE_BY_USER_ID_QUERY = "DELETE FROM user_has_category WHERE user_id = ?";
    private static final String SQL_DELETE_BY_CATEGORY_ID_QUERY = "DELETE FROM user_has_category WHERE category_id = ?";

    @Override
    public boolean insert(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, categoryId, userId);
    }

    @Override
    public boolean delete(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, categoryId, userId);
    }

    @Override
    public boolean deleteByUserId(Connection connection, int userId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_USER_ID_QUERY, userId);
    }

    @Override
    public boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_CATEGORY_ID_QUERY, categoryId);
    }

}
