package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.PostMetadataDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.entity.PostMetadata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class PostMetadataService implements BaseService<PostMetadata> {

    private DAOFactory factory = DAOFactory.getInstance();
    private PostMetadataDAO pmdao = factory.create(PostMetadataDAO.class);

    @Override
    public boolean insert(PostMetadata pm) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = pmdao.insert(connection, pm);
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
    public boolean update(int id, PostMetadata pm) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = pmdao.update(connection, id, pm);
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
                res = pmdao.delete(connection, id);
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
    public List<PostMetadata> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PostMetadata getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
