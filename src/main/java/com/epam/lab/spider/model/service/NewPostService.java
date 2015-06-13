package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.NewPostDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class NewPostService implements BaseService<NewPost> {

    private DAOFactory factory = DAOFactory.getInstance();
    private NewPostDAO npdao = factory.create(NewPostDAO.class);

    @Override
    public boolean insert(NewPost newPost) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = npdao.insert(connection, newPost);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(int id, NewPost newPost) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = npdao.update(connection, id, newPost);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
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
                res = npdao.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<NewPost> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NewPost getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
