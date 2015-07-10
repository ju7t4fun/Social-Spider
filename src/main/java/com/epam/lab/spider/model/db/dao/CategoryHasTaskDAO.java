package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.CategoryHasTask;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 13.06.2015.
 */
public interface CategoryHasTaskDAO {

    List<CategoryHasTask> select(Connection connection, String query, Object... args) throws SQLException;

    boolean insert(Connection connection, int categoryId, int taskId) throws SQLException;

    boolean delete(Connection connection, int categoryId, int taskId) throws SQLException;

    boolean deleteByTaskId(Connection connection, int taskId) throws SQLException;

    boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException;

    List<CategoryHasTask> getAllCHTByTaskId(Connection connection, int taskId) throws SQLException;

}
