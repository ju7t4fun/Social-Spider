package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public interface UserDAO extends CRUD<User> {

    User getByEmailAndPass(Connection connection, String email, String password) throws SQLException;

    User getByEmail(Connection connection, String email) throws SQLException;

    boolean checkPassword(Connection connection, String email, String password) throws SQLException;

    List<User> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    boolean updateByParameter(Connection connection,String name, String value, Integer id) throws SQLException;

    List<User> getAllUser(Connection connection) throws SQLException;
}
