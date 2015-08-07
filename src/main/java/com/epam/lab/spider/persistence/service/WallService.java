package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.TaskDestinationDAO;
import com.epam.lab.spider.persistence.dao.TaskSourceDAO;
import com.epam.lab.spider.persistence.dao.TaskHistoryDAO;
import com.epam.lab.spider.persistence.dao.WallDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */
public class WallService implements BaseService<Wall> {
    private static final Logger LOG = Logger.getLogger(WallService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private WallDAO wallDAO = factory.create(WallDAO.class);
    private TaskSourceDAO sourceDAO = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO destinationDAO = factory.create(TaskDestinationDAO.class);
    private TaskHistoryDAO synchronizedDataDAO = factory.create(TaskHistoryDAO.class);
    private PostingTaskService postingTaskService = new PostingTaskService();

    @Override
    public boolean insert(Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.insert(connection, wall);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.update(connection, id, wall);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateOnActive(int owner_id, int profile_id, Wall.Permission permission) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.updateOnActive(connection, owner_id, profile_id, permission);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean checkExist(Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.checkedExist(connection, wall);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public List<Wall> getAllByOwnerIdAndPermission(int owner_id, Wall.Permission permission) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getAllByOwnerIdAndPermission(connection, owner_id, permission);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                sourceDAO.deleteByWallId(connection, id);
                destinationDAO.deleteByWallId(connection, id);
                postingTaskService.deleteByWallId(connection, id);
                wallDAO.delete(connection, id);
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

    public boolean deleteWithConnection(Connection connection, int id) {

        try {
            List<Wall> walls = getAllByProfileIDWithConnection(connection, id);
            for (Wall wall : walls) {
                sourceDAO.deleteByWallId(connection, wall.getId());
                destinationDAO.deleteByWallId(connection, wall.getId());
                synchronizedDataDAO.deleteByWallId(connection, wall.getId());
                postingTaskService.deleteByWallId(connection, wall.getId());
                wallDAO.delete(connection, wall.getId());
            }

        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    public boolean deleteByOwnerId(Connection connection, int ownerId) {

        try {
            List<Wall> walls = getByOwnerIdWithConnection(connection, ownerId);
            for (Wall wall : walls) {
                sourceDAO.deleteByWallId(connection, wall.getId());
                destinationDAO.deleteByWallId(connection, wall.getId());
                synchronizedDataDAO.deleteByWallId(connection, wall.getId());
                postingTaskService.deleteByWallId(connection, wall.getId());
                wallDAO.delete(connection, wall.getId());
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }


    public boolean deleteByOwnerAndProfileIDAndPermission(int owner_id, int profile_id, int wallid, Wall.Permission
            permission) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
//                sourceDAO.deleteByWallId(connection, wallid);
//                destinationDAO.deleteByWallId(connection, wallid);
                wallDAO.deleteByOwnerId(connection, owner_id, profile_id, permission);
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
    public List<Wall> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Wall getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getDestinationByTaskId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getDestinationByTaskId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getAllByProfileID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getAllByProfileID(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getAllByProfileIDWithConnection(Connection connection, int id) {

        try {
            return wallDAO.getAllByProfileID(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getSourceByTaskId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getSourceByTaskId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getWallsByProfileId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getByProfileId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    // TODO: REFACTOR BY REAL USER CHECK BY USER ID
    public Wall getByIdAndLimitByUser(int id, int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getAllByUserID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return new ArrayList<>();
    }

    public List<Wall> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getReadByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getWriteByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getWriteByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getByOwnerId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getByOwnerId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getByOwnerIdWithConnection(Connection connection, int id) {
        try {
            return wallDAO.getByOwnerId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    public List<Wall> getReadByOwnerId(int ownerId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getReadByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getWriteByOwnerId(int ownerId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getWriteByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Wall> getWriteByAdmin() {
        try (Connection connection = PoolConnection.getConnection()) {
            return wallDAO.getWriteByAdmin(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
