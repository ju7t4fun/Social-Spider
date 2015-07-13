package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.TaskDestinationDAO;
import com.epam.lab.spider.model.db.dao.TaskSourceDAO;
import com.epam.lab.spider.model.db.dao.TaskSynchronizedDataDAO;
import com.epam.lab.spider.model.db.dao.WallDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class WallService implements BaseService<Wall> {

    private DAOFactory factory = DAOFactory.getInstance();
    private WallDAO wdao = factory.create(WallDAO.class);
    private TaskSourceDAO tsdao = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO tddao = factory.create(TaskDestinationDAO.class);
    private TaskSynchronizedDataDAO tsyncdao = factory.create(TaskSynchronizedDataDAO.class);
    private NewPostService newPostService = new NewPostService();

    @Override
    public boolean insert(Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.insert(connection, wall);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.update(connection, id, wall);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOnActive(int owner_id, int profile_id, Wall.Permission permission) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.updateOnActive(connection, owner_id, profile_id, permission);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExist(Wall wall) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.checkedExist(connection, wall);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Wall> getAllByOwnerIdAndPermission(int owner_id, Wall.Permission permission) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getAllByOwnerIdAndPermission(connection, owner_id, permission);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                tsdao.deleteByWallId(connection, id);
                tddao.deleteByWallId(connection, id);
                newPostService.deleteByWallId(connection, id);
                wdao.delete(connection, id);
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

    public boolean deleteWithConnection(Connection connection, int id) {

        try {
            List<Wall> walls = getAllByProfileIDWithConnection(connection, id);
            for (Wall wall : walls) {
                tsdao.deleteByWallId(connection, wall.getId());
                tddao.deleteByWallId(connection, wall.getId());
                tsyncdao.deleteByWallId(connection, wall.getId());
                newPostService.deleteByWallId(connection, wall.getId());
                wdao.delete(connection, wall.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteByOwnerId(Connection connection, int ownerId) {

        try {
            List<Wall> walls = getByOwnerIdWithConnection(connection, ownerId);
            for (Wall wall : walls) {
                tsdao.deleteByWallId(connection, wall.getId());
                tddao.deleteByWallId(connection, wall.getId());
                tsyncdao.deleteByWallId(connection, wall.getId());
                newPostService.deleteByWallId(connection, wall.getId());
                wdao.delete(connection, wall.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public boolean deleteByOwnerAndProfileIDAndPermission(int owner_id, int profile_id, int wallid, Wall.Permission
            permission) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
//                tsdao.deleteByWallId(connection, wallid);
//                tddao.deleteByWallId(connection, wallid);
                wdao.deleteByOwnerId(connection, owner_id, profile_id, permission);
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
    public List<Wall> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Wall getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getDestinationByTaskId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getDestinationByTaskId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getAllByProfileID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getAllByProfileID(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getAllByProfileIDWithConnection(Connection connection, int id) {

        try {
            return wdao.getAllByProfileID(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getSourceByTaskId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getSourceByTaskId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getWallsByProfileId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getByProfileId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: REFACTOR BY REAL USER CHECK BY USER ID
    public Wall getByIdAndLimitByUser(int id, int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getAllByUserID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Wall> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getReadByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getWriteByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getWriteByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getByOwnerId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getByOwnerId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getByOwnerIdWithConnection(Connection connection, int id) {
        try {
            return wdao.getByOwnerId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Wall> getReadByOwnerId(int ownerId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getReadByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getWriteByOwnerId(int ownerId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getWriteByOwnerId(connection, ownerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wall> getWriteByAdmin() {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getWriteByAdmin(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
