package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.SynchronizedData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public interface TaskSynchronizedInfoDAO<T extends SynchronizedData> {
    boolean createTable(Connection conn) throws SQLException;

    boolean insert(Connection connection, T sync) throws SQLException ;

    boolean update(Connection connection, T sync) throws SQLException ;

    boolean delete(Connection connection, Integer taskId, Integer wallId) throws SQLException;

    T getById(Connection connection, Integer taskId, Integer wallId) throws SQLException;
}
