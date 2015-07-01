package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Category;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public interface CategoryDAO extends CRUD<Category> {

    List<Category> getAllWithSearch(Connection connection, String nameToSearch) throws SQLException;

}
