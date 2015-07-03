package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.CategoryDAO;
import com.epam.lab.spider.model.db.entity.Category;

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
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM category ORDER BY id desc ";
    private static final String SQL_GET_ALL_WITH_SEARCH_QUERY = "SELECT * FROM category WHERE name LIKE ? " +
            " ORDER BY id desc ";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM category WHERE id = ?";
    private static final String SQL_GET_ALL_LIMITED_QUERY = "SELECT * FROM category ORDER BY id desc  LIMIT ?,?";
    private static final String SQL_GET_ALL_WITH_SEARCH_LIMITED_QUERY = "SELECT * FROM category WHERE name LIKE ? " +
            " ORDER BY id desc LIMIT ?, ?";
    private static final String SQL_GET_COUNT_WITH_SEARCH = "SELECT COUNT(*) FROM category WHERE " +
            " name LIKE ? ";
    private static final String SQL_GET_COUNT = "SELECT COUNT(*) FROM category";
    private static final String SQL_GET_BY_TASK_ID_QUERY = "SELECT category.* FROM  category JOIN category_has_task " +
            "ON category.id = category_has_task.category_id WHERE task_id = ?";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT category.* FROM category JOIN user_has_category ON" +
            " category.id = user_has_category.category_id WHERE user_id = ?";

    @Override
    public boolean insert(Connection connection, Category category) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                category.getName());
        category.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Category category) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                category.getName(), id);
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
        return categories;
    }

    @Override
    public List<Category> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public List<Category> getAllWithLimit(Connection connection, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_LIMITED_QUERY, start, ammount);
    }

    @Override
    public List<Category> getAllWithSearch(Connection connection, String nameToSearch) throws SQLException {
        return select(connection, SQL_GET_ALL_WITH_SEARCH_QUERY, nameToSearch);
    }

    @Override
    public List<Category> getAllWithSearchLimited(Connection connection, String nameToSearch, int start, int ammount)
            throws SQLException {
        return select(connection, SQL_GET_ALL_WITH_SEARCH_LIMITED_QUERY, nameToSearch, start, ammount);
    }

    @Override
    public Category getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public int getCountWithSearch(Connection connection, String categoryToSearch) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_WITH_SEARCH, categoryToSearch);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public int getCount(Connection connection) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public List<Category> getByTaskId(Connection connection, int taskId) throws SQLException {
        return select(connection, SQL_GET_BY_TASK_ID_QUERY, taskId);
    }

    @Override
    public List<Category> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

}
