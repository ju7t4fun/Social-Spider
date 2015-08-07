package com.epam.lab.spider.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public interface CRUD<T> {

    boolean insert(Connection connection, T t) throws SQLException;

    boolean update(Connection connection, int i, T t) throws SQLException;

    boolean delete(Connection connection, int id) throws SQLException;

    List<T> select(Connection connection, String query, Object... args) throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

    T getById(Connection connection, int id) throws SQLException;

}