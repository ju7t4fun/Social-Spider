package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.UserDAO;
import com.epam.lab.spider.model.db.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class UserService implements BaseService<User> {

    private DAOFactory factory = DAOFactory.getInstance();
    private UserDAO udao = factory.create(UserDAO.class);

    @Override
    public boolean insert(User user) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.insert(connection, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, User user) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.update(connection, id, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);

                assertTransaction(udao.delete(connection, id));
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


    public List<User> getWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getCountWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public User getByEmailAndPass(String email, String password) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getByEmailAndPass(connection, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByEmail(String email) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getByEmail(connection, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkPassword(String email, String password) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.checkPassword(connection, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateByParameter(String name, String value, Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.updateByParameter(connection, name, value, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
