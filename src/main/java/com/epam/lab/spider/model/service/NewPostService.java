package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.NewPostDAO;
import com.epam.lab.spider.model.dao.PostDAO;
import com.epam.lab.spider.model.dao.PostMetadataDAO;
import com.epam.lab.spider.model.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public class NewPostService implements BaseService<NewPost> {

    private DAOFactory factory = DAOFactory.getInstance();
    private NewPostDAO npdao = factory.create(NewPostDAO.class);
    private PostDAO pdao = factory.create(PostDAO.class);
    private PostMetadataDAO pmdao = factory.create(PostMetadataDAO.class);

    @Override
    public boolean insert(NewPost nPost) {
        boolean res = true;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                pdao.insert(connection, nPost.getPost());
                if (nPost.getMetadata() != null)
                    pmdao.insert(connection, nPost.getMetadata());
                res = npdao.insert(connection, nPost);
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
    public boolean update(int id, NewPost nPost) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                pdao.update(connection, nPost.getPost().getId(), nPost.getPost());
                if(nPost.getMetadata()!=null)pmdao.update(connection, nPost.getId(), nPost.getMetadata());
                npdao.update(connection, id, nPost);
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
                pmdao.delete(connection, npdao.getById(connection, id).getMetadata().getId());
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
        }
        return null;
    }

    @Override
    public NewPost getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<NewPost> getAllUnpostedByDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getAllUnpostedByDate(connection,date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
