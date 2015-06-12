package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.entity.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class CategoryDAOImp extends BaseDAO implements CategoryDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO category (name) VALUES (?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE category SET name = ?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM category WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM category";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM category WHERE id = ?";

    @Override
    public boolean insert(Connection connection, Category category) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, category.getName());
    }

    @Override
    public boolean update(Connection connection, int id, Category category) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, category.getName(), id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Category> select(Connection connection, String query, Object... args) throws SQLException {
        List<Category> categories = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Category category;
        while (rs.next()) {
            category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        return null;
    }

    @Override
    public List<Category> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Category getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }
}
