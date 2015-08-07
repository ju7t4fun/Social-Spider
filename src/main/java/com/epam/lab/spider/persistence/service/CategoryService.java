package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.UserHasCategory;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.CategoryDAO;
import com.epam.lab.spider.persistence.dao.CategoryHasPostDAO;
import com.epam.lab.spider.persistence.dao.CategoryHasTaskDAO;
import com.epam.lab.spider.persistence.dao.UserHasCategoryDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class CategoryService implements BaseService<Category> {
    private static final Logger LOG = Logger.getLogger(CategoryService.class);

    private static final DAOFactory factory = DAOFactory.getInstance();
    private CategoryDAO categoryDAO = factory.create(CategoryDAO.class);
    private CategoryHasTaskDAO categoryHasTaskDAO = factory.create(CategoryHasTaskDAO.class);
    private CategoryHasPostDAO categoryHasPostDAO = factory.create(CategoryHasPostDAO.class);
    private UserHasCategoryDAO userHasCategoryDAO = factory.create(UserHasCategoryDAO.class);


    @Override
    public boolean insert(Category category) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
//                assertTransaction(categoryDAO.insert(connection, category));
//                for (Task task : category.getTasks()) {
//                    assertTransaction(categoryHasTaskDAO.insert(connection, category.getId(), task.getId()));
//                }
//                connection.commit();
                categoryDAO.insert(connection, category);
                for (Task task : category.getTasks()) {
                    categoryHasTaskDAO.insert(connection, category.getId(), task.getId());
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public boolean update(int id, Category category) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
//                assertTransaction(categoryDAO.update(connection, id, category));
//                assertTransaction(categoryHasTaskDAO.deleteByCategoryId(connection, id));
//                for (Task task : category.getTasks()) {
//                    assertTransaction(categoryHasTaskDAO.insert(connection, id, task.getId()));
//                }
                categoryDAO.update(connection, id, category);
                categoryHasTaskDAO.deleteByCategoryId(connection, id);
                for (Task task : category.getTasks()) {
                    categoryHasTaskDAO.insert(connection, id, task.getId());
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                categoryHasTaskDAO.deleteByCategoryId(connection, id);
                userHasCategoryDAO.deleteByCatID(connection, id);
                categoryHasPostDAO.deleteByCategoryId(connection, id);
                categoryDAO.delete(connection, id);
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public List<Category> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


    @Override
    public Category getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


    public List<Category> getAllWithLimit(int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getAllWithLimit(connection, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Category> getAllWithSearchLimited(String nameToSearch, int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getAllWithSearchLimited(connection, nameToSearch, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getCount(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public int getCountAllWithSearch(String categoryToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getCountWithSearch(connection, categoryToSearch);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public List<Category> getByTaskId(int taskId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getByTaskId(connection, taskId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Category> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Category> getAllNonChosenCategories(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userHasCategoryDAO.getAllNonChosen(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Category> getAllChosenCategories(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userHasCategoryDAO.getAllChosen(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean deleteUserCategory(UserHasCategory us) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                userHasCategoryDAO.deleteByCatIDAndUserID(connection, us.getCategoryID(), us.getUserId());
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    public boolean insertIntoUserHasCategories(UserHasCategory usHas) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                userHasCategoryDAO.insert(connection, usHas.getCategoryID(), usHas.getUserId());
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    public List<Category> getByPostId(int postId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryDAO.getByPostId(connection, postId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}