package com.epam.lab.spider.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Marian Voronovskyi
 */
public interface TaskDestinationDAO {
    boolean insert(Connection connection, int taskId, int wallId) throws SQLException;

    boolean delete(Connection connection, int taskId, int wallId) throws SQLException;

    boolean deleteByTaskId(Connection connection, int taskId) throws SQLException;

    boolean deleteByWallId(Connection connection, int wallId) throws SQLException;
}
