package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.CategoryHasTaskDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 13.06.2015.
 */
public class CategoryHasTaskDAOImp extends BaseDAO implements CategoryHasTaskDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO category_has_task (category_id, task_id) VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM category_has_task WHERE category_id = ? AND task_id";
    private static final String SQL_DELETE_BY_TASK_ID = "DELETE FROM category_has_task WHERE task_id = ?";
    private static final String SQL_DELETE_BY_CATEGORY_ID = "DELETE FROM category_has_task WHERE category_id = ?";

    @Override
    public boolean insert(Connection connection, int categoryId, int taskId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, categoryId, taskId);
    }

    @Override
    public boolean delete(Connection connection, int categoryId, int taskId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, categoryId, taskId);
    }

    @Override
    public boolean deleteByTaskId(Connection connection, int taskId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_TASK_ID, taskId);
    }

    @Override
    public boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_CATEGORY_ID, categoryId);
    }

}
