package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.UserHasCategoryDAO;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.UserHasCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 02.07.2015.
 */
public class UserHasCategoryDAOImp extends BaseDAO implements UserHasCategoryDAO {



    private static final String SQL_INSERT_QUERY = "INSERT INTO user_has_category (category_id, user_id) VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM user_has_category WHERE category_id = ? AND user_id=?";

    private static final String SQL_GET_ALL_NON_CHOSEN = "SELECT * FROM category WHERE id NOT IN (SELECT " +
            "   category_id FROM user_has_category AS id WHERE user_id=?)";

    private static final String SQL_GET_ALL_CHOSEN = "SELECT * FROM category WHERE id IN (SELECT " +
            "   category_id FROM user_has_category AS id WHERE user_id=?)";


    @Override
    public boolean insert(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, categoryId, userId);
    }

    @Override
    public boolean deleteByCatIDAndUserID(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, categoryId, userId);
    }


    @Override
    public List<Category> getAllNonChosen(Connection connection, int userId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        ResultSet rs = selectQuery(connection, SQL_GET_ALL_NON_CHOSEN, userId);
        Category category;
        while (rs.next()) {
            category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setImageUrl(rs.getString("image_url"));
            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<Category> getAllChosen(Connection connection, int userId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        ResultSet rs = selectQuery(connection, SQL_GET_ALL_CHOSEN, userId);
        Category category;
        while (rs.next()) {
            category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setImageUrl(rs.getString("image_url"));
            categories.add(category);
        }
        return categories;
    }


}
