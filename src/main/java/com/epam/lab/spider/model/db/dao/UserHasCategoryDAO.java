package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.UserHasCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public interface UserHasCategoryDAO {

    boolean insert(Connection connection, int categoryId, int userId) throws SQLException;

    boolean deleteByCatIDAndUserID(Connection connection, int categoryId, int userId) throws SQLException;

    List<Category> getAllNonChosen(Connection connection, int userId) throws SQLException;

    List<Category> getAllChosen(Connection connection, int userId) throws SQLException;

}
