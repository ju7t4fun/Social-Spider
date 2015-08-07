package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public interface UserHasCategoryDAO {

    boolean insert(Connection connection, int categoryId, int userId) throws SQLException;

    boolean deleteByCatIDAndUserID(Connection connection, int categoryId, int userId) throws SQLException;

    List<Category> getAllNonChosen(Connection connection, int userId) throws SQLException;

    List<Category> getAllChosen(Connection connection, int userId) throws SQLException;

    boolean deleteByCatID(Connection connection, int categoryId) throws SQLException;

}
