package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.NewPostDAO;
import com.epam.lab.spider.model.db.dao.PostDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.savable.SavableService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public class NewPostService implements BaseService<NewPost>, SavableService<NewPost> {

    private DAOFactory factory = DAOFactory.getInstance();
    private NewPostDAO npdao = factory.create(NewPostDAO.class);
    private PostDAO pdao = factory.create(PostDAO.class);
    private PostService ps = new PostService();

    @Override
    public boolean save(NewPost entity) throws InvalidEntityException, UnsupportedDAOException,
            ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(NewPost entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException,
            ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.customSave(conn, entity, new Object[]{entity.getPost()}, new Object[]{});
    }

    @Override
    public boolean insert(NewPost nPost) {
        boolean res = true;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                if (nPost.getPostId() == null) {
                    ps.insert(connection, nPost.getPost());
                    nPost.setPostId(nPost.getPost().getId());
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
                if (nPost.getPostId() == null) {
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
    public List<NewPost> getAllUndeletedByDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getAllUndeletedByDate(connection, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setSpecialStageByOwner(Integer ownerId, NewPost.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == NewPost.State.ERROR) {
                return npdao.setErrorStateByOwner(connection, ownerId);
            }
//            else if (state == NewPost.State.RESTORED) {
//                return npdao.setRestoredStateByOwner(connection, ownerId);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setSpecialStageByWall(Integer wallId, NewPost.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == NewPost.State.ERROR) {
                return npdao.setErrorStateByWall(connection, wallId);
            } else if (state == NewPost.State.RESTORED) {
                return npdao.setRestoredStateByWall(connection, wallId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setSpecialStageByProfile(Integer profileId, NewPost.State state) {
        try (Connection connection = PoolConnection.getConnection()) {
            if (state == NewPost.State.ERROR) {
                return npdao.setErrorStateByProfile(connection, profileId);
            }
//            else if (state == NewPost.State.RESTORED) {
//                return npdao.setRestoredStateByProfile(connection, profileId);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setRestoredStageByWall(Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.setRestoredStateByWall(connection, wallId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStage(NewPost nPost) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.updateState(connection, nPost.getId(), nPost.getState());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NewPost> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessageByID(int newpost_id) {

        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getMessageById(connection, newpost_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "bad message";
    }

    public List<NewPost> getAllWithQuery(String someQuery) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getAllWithQuery(connection, someQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountWithQuery(String query) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getCountWithQuery(connection, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getPostedCountByUserIdWithSearch(Integer id, String sSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getPostedCountByUserIdWithSearch(connection, id, sSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<NewPost> getPostedByUserIdWithSearch(Integer id, int page, int size, String sSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getPostedByUserIdWithSearch(connection, id, page, size, sSearch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NewPost> getPostedByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getPostedByUserId(connection, id, page, size);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPostedCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getPostedCountByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<NewPost> getByUserIdWithParameters(Integer id, int offset, int limit, String type, String q, String
            order, Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getByUserIdWithParameters(connection, id, offset, limit, type, q, order, wallId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountAllByUserIdWithParameters(Integer id, String type, String q, Integer wallId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.getCountAllByUserIdWithParameters(connection, id, type, q, wallId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Map<Long, Integer> statisticsPosting(String date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return npdao.statisticsPosting(connection, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
