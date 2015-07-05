package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public interface CategoryDAO extends CRUD<Category> {


    List<Category> getAllWithLimit(Connection connection, int start, int ammount) throws SQLException;

    List<Category> getAllWithSearchLimited(Connection connection, String q, int start, int ammount) throws SQLException;

    int getCountWithSearch(Connection connection, String categoryToSearch) throws SQLException;

    int getCount(Connection connection) throws SQLException;

    List<Category> getByTaskId(Connection connection, int taskId) throws SQLException;

    List<Category> getByUserId(Connection connection, int userId) throws SQLException;


}
