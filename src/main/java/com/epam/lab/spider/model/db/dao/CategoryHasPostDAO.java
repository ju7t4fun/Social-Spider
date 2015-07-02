package com.epam.lab.spider.model.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public interface CategoryHasPostDAO {

    boolean insert(Connection connection, int categoryId, int postId) throws SQLException;

    boolean delete(Connection connection, int categoryId, int postId) throws SQLException;

    boolean deleteByPostId(Connection connection, int postId) throws SQLException;

    boolean deleteByCategoryId(Connection connection, int categoryId) throws SQLException;

}
