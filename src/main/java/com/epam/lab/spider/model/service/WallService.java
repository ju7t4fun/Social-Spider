package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.TaskDestinationDAO;
import com.epam.lab.spider.model.dao.TaskSourceDAO;
import com.epam.lab.spider.model.dao.WallDAO;
import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public boolean delete(int id) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                tsdao.deleteByWallId(connection, id);
                tddao.deleteByWallId(connection, id);
                res = wdao.delete(connection, id);
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

    public List<Wall> getSourceByTaskId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return wdao.getSourceByTaskId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
