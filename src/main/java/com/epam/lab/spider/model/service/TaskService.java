package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.TaskDAO;
import com.epam.lab.spider.model.dao.TaskDestinationDAO;
import com.epam.lab.spider.model.dao.TaskSourceDAO;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class TaskService implements BaseService<Task> {

    private DAOFactory factory = DAOFactory.getInstance();
    private TaskDAO tdao = factory.create(TaskDAO.class);
    private TaskSourceDAO tsdao = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO tddao = factory.create(TaskDestinationDAO.class);

    @Override
    public boolean insert(Task task) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = tdao.insert(connection, task);
                Set<Wall> walls = task.getSource();
                for (Wall wall : walls) {
                    tsdao.insert(connection, task.getId(), wall.getId());
                }
                walls = task.getDestination();
                for (Wall wall : walls) {
                    tddao.insert(connection, task.getId(), wall.getId());
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(int id, Task task) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = tdao.update(connection, id, task);
                if (task.getSource() != null) {
                    tsdao.deleteByTaskId(connection, id);
                    Set<Wall> walls = task.getSource();
                    for (Wall wall : walls) {
                        tsdao.insert(connection, id, wall.getId());
                    }
                }
                if (task.getDestination() != null) {
                    tddao.deleteByTaskId(connection, id);
                    Set<Wall> walls = task.getDestination();
                    for (Wall wall : walls) {
                        tddao.insert(connection, id, wall.getId());
                    }
                }
                connection.commit();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
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
                tsdao.deleteByTaskId(connection, id);
                tddao.deleteByTaskId(connection, id);
                res = tdao.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Task> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Task getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getByCategoryId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByCategoryId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
