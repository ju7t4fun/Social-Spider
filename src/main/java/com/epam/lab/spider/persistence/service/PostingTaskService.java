package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.PostDAO;
import com.epam.lab.spider.persistence.dao.PostingTaskDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtils;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.epam.lab.spider.persistence.SQLTransactionException.assertTransaction;

/**
 * @author Boyarsky Vitaliy
 * @author Yura Kovalik
 */
public class PostingTaskService implements BaseService<PostingTask>, SavableService<PostingTask> {
    private static final Logger LOG = Logger.getLogger(PostingTaskService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private PostingTaskDAO postingTaskDAO = factory.create(PostingTaskDAO.class);
    private PostDAO postDAO = factory.create(PostDAO.class);
    private PostService ps = new PostService();

    @Override
    public boolean save(PostingTask entity) throws InvalidEntityException, UnsupportedDAOException,
            ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtils.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(PostingTask entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException,
            ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtils.customSave(conn, entity, new PersistenceIdentifiable[]{entity.getPost()}, new PersistenceIdentifiable[]{});
    }

    @Override
    public boolean insert(PostingTask nPost) {
        boolean res = true;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                if (nPost.getPostId() == null) {
                    ps.insert(connection, nPost.getPost());
                    nPost.setPostId(nPost.getPost().getId());
                }
                assertTransaction(postingTaskDAO.insert(connection, nPost));
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
    public boolean update(int id, PostingTask nPost) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                if (nPost.getPostId() == null) {
                    assertTransaction(postDAO.insert(connection, nPost.getPost()));
                }
                assertTransaction(postingTaskDAO.update(connection, id, nPost));
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
//                assertTransaction(postingTaskDAO.delete(connection, id));
                postingTaskDAO.delete(connection, id);
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

    public boolean deleteByWallId(Connection connection, int wallId) {
        try {
            postingTaskDAO.deleteByWallId(connection, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public List<PostingTask> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public PostingTask getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<PostingTask> getAllUnpostedByDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getAllUnpostedByDate(connection, date);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<PostingTask> getAllUndeletedByDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getAllUndeletedByDate(connection, date);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean setSpecialStageByOwner(Integer ownerId, PostingTaskImpl.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == PostingTaskImpl.State.ERROR) {
                return postingTaskDAO.setErrorStateByOwner(connection, ownerId);
            }
//            else if (state == PostingTask.State.RESTORED) {
//                return postingTaskDAO.setRestoredStateByOwner(connection, ownerId);
//            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean setSpecialStageByWall(Integer wallId, PostingTaskImpl.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == PostingTaskImpl.State.ERROR) {
                return postingTaskDAO.setErrorStateByWall(connection, wallId);
            } else if (state == PostingTaskImpl.State.RESTORED) {
                return postingTaskDAO.setRestoredStateByWall(connection, wallId);
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean setSpecialStageByProfile(Integer profileId, PostingTaskImpl.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == PostingTaskImpl.State.ERROR) {
                return postingTaskDAO.setErrorStateByProfile(connection, profileId);
            }
//            else if (state == PostingTask.State.RESTORED) {
//                return postingTaskDAO.setRestoredStateByProfile(connection, profileId);
//            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean setRestoredStageByWall(Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.setRestoredStateByWall(connection, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateStage(PostingTask nPost) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.updateState(connection, nPost.getId(), nPost.getState());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public List<PostingTask> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<PostingTask> getByUserId(int id, int offset, int count) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getByUserIdWithLimits(connection, id, offset, count);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return new ArrayList<>();
    }

    public String getMessageByID(int newpost_id) {

        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getMessageById(connection, newpost_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return "bad message";
    }

    public List<PostingTask> getAllWithQuery(String someQuery) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getAllWithQuery(connection, someQuery);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountWithQuery(String query) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getCountWithQuery(connection, query);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public int getPostedCountByUserIdWithSearch(Integer id, String sSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getPostedCountByUserIdWithSearch(connection, id, sSearch);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return -1;
    }

    public List<PostingTask> getPostedByUserIdWithSearch(Integer id, int page, int size, String sSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getPostedByUserIdWithSearch(connection, id, page, size, sSearch);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return null;
    }

    public List<PostingTask> getPostedByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getPostedByUserId(connection, id, page, size);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return null;
    }

    public int getPostedCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getPostedCountByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<PostingTask> getByUserIdWithParameters(Integer id, int offset, int limit, String type, String q, String
            order, Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getByUserIdWithParameters(connection, id, offset, limit, type, q, order, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountAllByUserIdWithParameters(Integer id, String type, String q, Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.getCountAllByUserIdWithParameters(connection, id, type, q, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public Map<Long, Integer> statisticsPosting(String date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return postingTaskDAO.statisticsPosting(connection, date);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
