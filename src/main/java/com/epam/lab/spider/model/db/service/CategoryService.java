package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.CategoryDAO;
import com.epam.lab.spider.model.db.dao.CategoryHasTaskDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Category;
import com.epam.lab.spider.model.db.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class CategoryService implements BaseService<Category> {

    private static final DAOFactory factory = DAOFactory.getInstance();
    private CategoryDAO cdao = factory.create(CategoryDAO.class);
    private CategoryHasTaskDAO chtdao = factory.create(CategoryHasTaskDAO.class);

    @Override
    public boolean insert(Category category) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(cdao.insert(connection, category));
                for (Task task : category.getTasks()) {
                    assertTransaction(chtdao.insert(connection, category.getId(), task.getId()));
                }
                connection.commit();
            } catch (SQLTransactionException e) {
                connection.rollback();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean update(int id, Category category) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(cdao.update(connection, id, category));
                assertTransaction(chtdao.deleteByCategoryId(connection, id));
                for (Task task : category.getTasks()) {
                    assertTransaction(chtdao.insert(connection, id, task.getId()));
                }
                connection.commit();
            } catch (SQLTransactionException e) {
                connection.rollback();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
//                connection.setAutoCommit(false);
//                assertTransaction(chtdao.deleteByCategoryId(connection, id));
//                assertTransaction(cdao.delete(connection, id));
//                connection.commit();
                chtdao.deleteByCategoryId(connection, id);
                cdao.delete(connection, id);
            } catch (SQLTransactionException e) {
                connection.rollback();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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


    public List<Category> getAllWithSearch(String nameToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getAllWithSearch(connection, nameToSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllWithLimit(int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getAllWithLimit(connection, start, ammount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllWithSearchLimited(String nameToSearch, int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getAllWithSearchLimited(connection, nameToSearch, start, ammount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getCount(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCountAllWithSearch(String categoryToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getCountWithSearch(connection, categoryToSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Category> getByTaskId(int taskId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getByTaskId(connection, taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return cdao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}