package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.TaskSourceDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public class TaskSourceDAOImp extends BaseDAO implements TaskSourceDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO task_source (task_id, wall_id) " +
            "VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM task_source WHERE task_id = ? AND " +
            "wall_id = ?";
    private static final String SQL_DELETE_BY_TASK_ID = "DELETE FROM task_source WHERE task_id = ?";
    private static final String SQL_DELETE_BY_WALL_ID = "DELETE FROM task_source WHERE wall_id = ?";

    @Override
    public boolean insert(Connection connection, int taskId, int wallId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, taskId, wallId);
    }

    @Override
    public boolean delete(Connection connection, int taskId, int wallId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, taskId, wallId);
    }

    @Override
    public boolean deleteByTaskId(Connection connection, int taskId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_TASK_ID, taskId);
    }

    @Override
    public boolean deleteByWallId(Connection connection, int wallId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_WALL_ID, wallId);
    }
}