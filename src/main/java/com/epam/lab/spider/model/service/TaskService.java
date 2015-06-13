package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.TaskDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class TaskService implements BaseService<Task> {

    private DAOFactory factory = DAOFactory.getInstance();
    private TaskDAO tdao = factory.create(TaskDAO.class);


    @Override
    public boolean insert(Task task) {
        boolean res = false;
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                res = tdao.insert(connection, task);
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
            return null;
        }
    }

    @Override
    public Task getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> getByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> getByCategoryId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByCategoryId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
