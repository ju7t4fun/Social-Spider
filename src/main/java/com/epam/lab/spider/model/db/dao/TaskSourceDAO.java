package com.epam.lab.spider.model.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Marian Voronovskyi on 13.06.2015.
 */
public interface TaskSourceDAO {

    boolean insert(Connection connection, int taskId, int wallId) throws SQLException;

    boolean delete(Connection connection, int taskId, int wallId) throws SQLException;

    boolean deleteByTaskId(Connection connection, int taskId) throws SQLException;

    boolean deleteByWallId(Connection connection, int wallId) throws SQLException;
}
