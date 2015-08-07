package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.UserActions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public interface UserActionsDAO<T extends UserActions> {

    boolean createTable(Connection conn) throws SQLException;

    boolean insert(Connection connection, T t, Date sqlDate) throws SQLException ;

    boolean update(Connection connection, T t) throws SQLException ;

    boolean delete(Connection connection, Integer id) throws SQLException;

    T getById(Connection connection, Integer id) throws SQLException;

    T getByUserId(Connection connection, Integer userId, Date sqlDate) throws SQLException;
}
