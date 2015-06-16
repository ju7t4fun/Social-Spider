package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.NewPostDAO;
import com.epam.lab.spider.model.db.dao.PostDAO;
import com.epam.lab.spider.model.db.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public class NewPostService implements BaseService<NewPost> {

    private DAOFactory factory = DAOFactory.getInstance();
    private NewPostDAO npdao = factory.create(NewPostDAO.class);
    private PostDAO pdao = factory.create(PostDAO.class);

    @Override
    public boolean insert(NewPost nPost) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                if (nPost.getPost().getId() == null) {
                    assertTransaction(pdao.insert(connection, nPost.getPost()));
                } else {
                    if (!pdao.getById(connection, nPost.getPostId()).equals(nPost.getPost())) {
                        assertTransaction(pdao.insert(connection, nPost.getPost()));
                    }
                }
                assertTransaction(npdao.insert(connection, nPost));
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
    public boolean update(int id, NewPost nPost) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                if (nPost.getPostId() != null) {
                    if (!pdao.getById(connection, nPost.getPostId()).equals(nPost.getPost())) {
                        assertTransaction(pdao.insert(connection, nPost.getPost()));
                    }
                } else {
                    assertTransaction(pdao.insert(connection, nPost.getPost()));
                }
                assertTransaction(npdao.update(connection, id, nPost));
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
                connection.setAutoCommit(false);
                assertTransaction(npdao.delete(connection, id));
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
            return npdao.getAllUnpostedByDate(connection, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
