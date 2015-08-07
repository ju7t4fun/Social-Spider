package com.epam.lab.spider.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Boyarsky Vitaliy
 */
public interface CategoryHasPostDAO {

    boolean insert(Connection connection, int categoryId, int postId) throws SQLException;

    boolean delete(Connection connection, int categoryId, int postId) throws SQLException;

    boolean deleteByPostId(Connection connection, int postId) throws SQLException;

    boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException;

}
