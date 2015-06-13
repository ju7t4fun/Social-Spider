package com.epam.lab.spider.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 13.06.2015.
 */
public interface CategoryHasTaskDAO {

    boolean insert(Connection connection, int categoryId, int taskId) throws SQLException;

    boolean delete(Connection connection, int categoryId, int taskId) throws SQLException;

    boolean deleteByTaskId(Connection connection, int taskId) throws SQLException;

    boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException;

}
