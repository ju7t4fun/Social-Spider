package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.UserDAO;
import com.epam.lab.spider.model.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public class UserService implements BaseService<User> {
    private static final Logger LOG = Logger.getLogger(UserService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private UserDAO userDAO = factory.create(UserDAO.class);

    @Override
    public boolean insert(User user) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.insert(connection, user);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, User user) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.update(connection, id, user);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }



    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);

                SQLTransactionException.assertTransaction(userDAO.delete(connection, id));
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


    public List<User> getWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getCountWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public User getByEmailAndPass(String email, String password) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getByEmailAndPass(connection, email, password);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public User getByEmail(String email) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getByEmail(connection, email);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public User getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean checkPassword(String email, String password) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.checkPassword(connection, email, password);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateByParameter(String name, String value, Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.updateByParameter(connection, name, value, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }


    public List<User> getAllUser() {
        try (Connection connection = PoolConnection.getConnection()) {
            return userDAO.getAllUser(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
