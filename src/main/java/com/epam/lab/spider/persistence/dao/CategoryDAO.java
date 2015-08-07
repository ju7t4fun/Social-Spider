package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */
public interface CategoryDAO extends CRUD<Category> {


    List<Category> getAllWithLimit(Connection connection, int start, int amount) throws SQLException;

    List<Category> getAllWithSearchLimited(Connection connection, String q, int start, int amount) throws SQLException;

    int getCountWithSearch(Connection connection, String categoryToSearch) throws SQLException;

    int getCount(Connection connection) throws SQLException;

    List<Category> getByTaskId(Connection connection, int taskId) throws SQLException;

    List<Category> getByUserId(Connection connection, int userId) throws SQLException;


    List<Category> getByPostId(Connection connection, int postId) throws SQLException;
}
