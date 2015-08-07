package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.AttachmentDAO;
import com.epam.lab.spider.persistence.dao.CategoryHasPostDAO;
import com.epam.lab.spider.persistence.dao.PostDAO;
import com.epam.lab.spider.persistence.dao.PostingTaskDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.epam.lab.spider.persistence.SQLTransactionException.assertTransaction;

/**
 * @author Oleksandra Lobanok
 */
public class PostService implements BaseService<Post>, SavableService<Post> {
    private static final Logger LOG = Logger.getLogger(PostService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private PostDAO postDAO = factory.create(PostDAO.class);
    private AttachmentDAO attachmentDAO = factory.create(AttachmentDAO.class);
    private CategoryHasPostDAO categoryHasPostDAO = factory.create(CategoryHasPostDAO.class);
    private PostingTaskDAO postingTaskDAO = factory.create(PostingTaskDAO.class);

    @Override
    public boolean save(Post entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException,
            UnsupportedServiceException {
        return SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Post entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException,
            ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.customSave(conn, entity, new Object[]{}, new Object[]{entity.getAttachments()});
    }

    @Override
    public boolean insert(Post post) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(postDAO.insert(connection, post));
                for (Attachment attachment : post.getAttachments()) {
                    attachment.setPostId(post.getId());
                    assertTransaction(attachmentDAO.insert(connection, attachment));
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

    public void insert(Connection connection, Post post) throws SQLException {
        assertTransaction(postDAO.insert(connection, post));
        for (Attachment attachment : post.getAttachments()) {
            attachment.setPostId(post.getId());
            assertTransaction(attachmentDAO.insert(connection, attachment));
        }
    }

    @Override
    public boolean update(int id, Post post) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                Set<Attachment> attachments = postDAO.getById(connection, id).getAttachments();
                if (attachments.size() > 0) {
                    assertTransaction(attachmentDAO.deleteByPostId(connection, id));
                }
                for (Attachment attachment : post.getAttachments()) {
                    assertTransaction(attachmentDAO.insert(connection, attachment));
                }
                assertTransaction(postDAO.update(connection, id, post));
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
                if (postDAO.getById(connection, id).getAttachments().size() > 0) {
                    attachmentDAO.deleteByPostId(connection, id);
                }
                categoryHasPostDAO.deleteByPostId(connection, id);
                postingTaskDAO.deleteByPostId(connection, id);
                postDAO.delete(connection, id);
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
    public List<Post> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Post getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Post> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Post> getAllNotInNewPost() {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getAllNotInNewPost(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Post> getByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getByUserId(connection, id, page, size);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getCountByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Post> getByUserIdWithSearch(int userId, int page, int size, String messageToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getByUserIdWithSearch(connection, userId, page, size, messageToSearch);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountByUserIdWithSearch(Integer id, String messageToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getCountByUserIdWithSearch(connection, id, messageToSearch);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Post> getByCategoryId(int categoryId, int offset, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getByCategoryId(connection, categoryId, offset, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean bindWithCategories(int postId, List<Category> categories) {
        CategoryHasPostDAO chpdao = factory.create(CategoryHasPostDAO.class);
        try (Connection connection = PoolConnection.getConnection()) {
            for (Category category : categories) {
                chpdao.insert(connection, category.getId(), postId);
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    public List<Integer> getByCategoryFromUser(int userId, int offset, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postDAO.getByCategoryFromUser(connection, userId, offset, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

}
