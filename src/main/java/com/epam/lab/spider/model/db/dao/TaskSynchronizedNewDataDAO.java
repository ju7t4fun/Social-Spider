package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.SynchronizedData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hell-engine on 7/10/2015.
 */
public interface TaskSynchronizedNewDataDAO<T extends SynchronizedData> {
    boolean createTable(Connection conn) throws SQLException;

    boolean insert(Connection connection, T sync) throws SQLException ;

    boolean update(Connection connection, T sync) throws SQLException ;

    boolean delete(Connection connection, Integer taskId, Integer wallId) throws SQLException;

    T getById(Connection connection, Integer taskId, Integer wallId) throws SQLException;
}
