package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);

                res = udao.delete(connection, id);
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
    public List<User> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByEmailAndPass(String email, String password) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getByEmailAndPass(connection, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getByEmail(String email) {
        try (Connection connection = PoolConnection.getConnection()) {
            return udao.getByEmail(connection, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

}
