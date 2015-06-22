package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public interface UserDAO extends CRUD<User> {

    User getByEmailAndPass(Connection connection, String email, String password) throws SQLException;

    User getByEmail(Connection connection, String email) throws SQLException;

    boolean checkPassword(Connection connection, String email, String password) throws SQLException;

    boolean updateByParameter(Connection connection,String name, String value, Integer id) throws SQLException;

}
