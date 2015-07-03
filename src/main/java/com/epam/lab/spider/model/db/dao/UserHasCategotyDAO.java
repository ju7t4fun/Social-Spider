package com.epam.lab.spider.model.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public interface UserHasCategotyDAO {

    boolean insert(Connection connection, int categoryId, int userId) throws SQLException;

    boolean delete(Connection connection, int categoryId, int userId) throws SQLException;

    boolean deleteByUserId(Connection connection, int userId) throws SQLException;

    boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException;

}
