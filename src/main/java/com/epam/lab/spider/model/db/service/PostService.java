package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.AttachmentDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.PostDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.savable.SavableService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;
import com.sun.xml.internal.fastinfoset.stax.events.StAXEventAllocatorBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Sasha on 12.06.2015.
 */
public class PostService implements BaseService<Post>,SavableService<Post> {

    private DAOFactory factory = DAOFactory.getInstance();
    private PostDAO pdao = factory.create(PostDAO.class);
    private AttachmentDAO adao = factory.create(AttachmentDAO.class);

    @Override
    public boolean save(Post entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.saveFromInterface(entity,this);
    }

    @Override
    public boolean save(Post entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.customSave(conn, entity, new Object[]{}, new Object[]{entity.getAttachments()});
    }
    @Deprecated
    @Override
    public boolean insert(Post post) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(pdao.insert(connection, post));
                for (Attachment attachment : post.getAttachments()) {
                    attachment.setPostId(post.getId());
                    assertTransaction(adao.insert(connection, attachment));
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
    @Deprecated
    @Override
    public boolean update(int id, Post post) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(adao.deleteByPostId(connection, id));
                for (Attachment attachment : post.getAttachments()) {
                    assertTransaction(adao.insert(connection, attachment));
                }
                assertTransaction(pdao.update(connection, id, post));
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
                assertTransaction(adao.deleteByPostId(connection, id));
                assertTransaction(pdao.delete(connection, id));
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
