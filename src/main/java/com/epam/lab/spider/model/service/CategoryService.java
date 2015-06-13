package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.CategoryDAO;
import com.epam.lab.spider.model.dao.CategoryHasTaskDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class CategoryService implements BaseService<Category> {

    private DAOFactory factory = DAOFactory.getInstance();
    private CategoryDAO cdao = factory.create(CategoryDAO.class);
    private CategoryHasTaskDAO chtdao = factory.create(CategoryHasTaskDAO.class);

    @Override
    public boolean insert(Category category) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = cdao.insert(connection, category);
                List<Task> tasks = category.getTasks();
                for (Task task : tasks) {
                    chtdao.insert(connection, category.getId(), task.getId());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(int id, Category category) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = cdao.update(connection, id, category);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(int id) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = chtdao.deleteByCategoryId(connection, id);
                res = res && cdao.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Category> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}