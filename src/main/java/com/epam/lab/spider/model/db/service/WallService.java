package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.TaskDestinationDAO;
import com.epam.lab.spider.model.db.dao.TaskSourceDAO;
import com.epam.lab.spider.model.db.dao.WallDAO;
import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Sasha on 12.06.2015.
 */
public class WallService implements BaseService<Wall> {

    private DAOFactory factory = DAOFactory.getInstance();
    private WallDAO wdao = factory.create(WallDAO.class);
    private TaskSourceDAO tsdao = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO tddao = factory.create(TaskDestinationDAO.class);

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
            return wdao.getAllByOwnerIdAndPermission(connection,owner_id,permission);
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

    public boolean deleteByOwnerAndProfileIDAndPermission(int owner_id,int profile_id, int wallid, Wall.Permission permission) {
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

    public  List<Wall> getAllByProfileID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
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
    /**
     * REWRITE THIS METHOD TO USED user id
     * @param id
     * @param userId
     * @return
     */
    public Wall getByIdAndUserLimit(int id, int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  List<Wall> getAllByUserID(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
