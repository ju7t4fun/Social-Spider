package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.persistence.dao.CategoryDAO;
import com.epam.lab.spider.persistence.dao.UserHasCategoryDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class UserHasCategoryDAOImp extends BaseDAO implements UserHasCategoryDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO user_has_category (category_id, user_id) VALUES (?, ?)";
    private static final String SQL_DELETE_QUERY = "DELETE FROM user_has_category WHERE category_id = ? AND user_id=?";

    private static final String SQL_DELETE_ByCatId_QUERY = "DELETE FROM user_has_category WHERE category_id = ?" ;

    private static final String SQL_GET_ALL_NON_CHOSEN = "SELECT * FROM category WHERE id NOT IN (SELECT " +
            "   category_id FROM user_has_category AS id WHERE user_id=?)";

    private static final String SQL_GET_ALL_CHOSEN = "SELECT * FROM category WHERE id IN (SELECT " +
            "   category_id FROM user_has_category AS id WHERE user_id=?)";

    @Deprecated
    @Override
    public boolean insert(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, categoryId, userId);
    }

    @Deprecated
    @Override
    public boolean deleteByCatIDAndUserID(Connection connection, int categoryId, int userId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, categoryId, userId);
    }

    @Deprecated
    @Override
    public boolean deleteByCatID(Connection connection, int categoryId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_ByCatId_QUERY, categoryId);
    }


    @Deprecated
    @Override
    public List<Category> getAllNonChosen(Connection connection, int userId) throws SQLException {
        return DAOFactory.getInstance().create(CategoryDAO.class).select(connection, SQL_GET_ALL_NON_CHOSEN, userId);
    }

    @Deprecated
    @Override
    public List<Category> getAllChosen(Connection connection, int userId) throws SQLException {
        return DAOFactory.getInstance().create(CategoryDAO.class).select(connection, SQL_GET_ALL_CHOSEN, userId);
    }


}
