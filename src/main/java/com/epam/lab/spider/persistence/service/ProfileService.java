package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.ProfileDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @author Boyarsky Vitaliy
 */
public class ProfileService implements BaseService<Profile> {
    private static final Logger LOG = Logger.getLogger(PostService.class);

    private static DAOFactory factory = DAOFactory.getInstance();
    private static ProfileDAO profileDAO = factory.create(ProfileDAO.class);
    private  static  WallService wallService = ServiceFactory.getInstance().create(WallService.class);

    @Override
    public boolean insert(Profile profile) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.insert(connection, profile);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Profile profile) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.update(connection, id, profile);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                wallService.deleteWithConnection(connection, id);
                profileDAO.delete(connection, id);
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
    public List<Profile> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Profile getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public Profile getByVkId(int vkId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getByVkId(connection, vkId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public List<Profile> getProfilesNotInWall(int owner_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getNotInWall(connection, owner_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public List<Profile> getProfilesInWall(int owner_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getInWall(connection, owner_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public List<Profile> getByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean isByVkId(int id) {
        return getByVkId(id) != null;
    }

    public List<Profile> getByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getByUserId(connection, id, page, size);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return profileDAO.getCountByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
}
