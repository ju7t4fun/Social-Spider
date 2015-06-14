package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.AttachmentDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.PostDAO;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Post;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class PostService implements BaseService<Post> {

    private DAOFactory factory = DAOFactory.getInstance();
    private PostDAO pdao = factory.create(PostDAO.class);
    private AttachmentDAO adao = factory.create(AttachmentDAO.class);

    @Override
    public boolean insert(Post post) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                pdao.insert(connection, post);
                List<Attachment> attachments = post.getAttachments();
                for (Attachment attachment : attachments) {
                    adao.insert(connection, attachment);
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
    public boolean update(int id, Post post) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = pdao.update(connection, id, post);
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
                List<Attachment> attachments = pdao.getById(connection, id).getAttachments();
                for (Attachment attachment : attachments) {
                    adao.delete(connection, attachment.getId());
                }
                res = pdao.delete(connection, id);
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
    public List<Post> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Post getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
