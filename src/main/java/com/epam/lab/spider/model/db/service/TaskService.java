package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.*;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class TaskService implements BaseService<Task> {

    private static DAOFactory factory = DAOFactory.getInstance();
    private TaskDAO tdao = factory.create(TaskDAO.class);
    private TaskSourceDAO tsdao = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO tddao = factory.create(TaskDestinationDAO.class);
    private CategoryHasTaskDAO chtdao = factory.create(CategoryHasTaskDAO.class);
    private FilterDAO fdao = factory.create(FilterDAO.class);

    @Override
    public boolean insert(Task task) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(tdao.insert(connection, task));
                assertTransaction(fdao.insert(connection, task.getFilter()));
                for (Wall wall : task.getSource()) {
                    assertTransaction(tsdao.insert(connection, task.getId(), wall.getId()));
                }
                for (Wall wall : task.getDestination()) {
                    assertTransaction(tddao.insert(connection, task.getId(), wall.getId()));
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
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean update(int id, Task task) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(tdao.update(connection, id, task));
                assertTransaction(fdao.update(connection, task.getFilterId(), task.getFilter()));
                assertTransaction(tsdao.deleteByTaskId(connection, id));
                for (Wall wall : task.getSource()) {
                    assertTransaction(tsdao.insert(connection, id, wall.getId()));
                }
                assertTransaction(tddao.deleteByTaskId(connection, id));
                for (Wall wall : task.getDestination()) {
                    assertTransaction(tddao.insert(connection, id, wall.getId()));
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
                assertTransaction(fdao.delete(connection, tdao.getById(connection, id).getFilterId()));
                assertTransaction(tddao.deleteByTaskId(connection, id));
                assertTransaction(tsdao.deleteByTaskId(connection, id));
                assertTransaction(chtdao.deleteByTaskId(connection, id));
                assertTransaction(tdao.delete(connection, id));
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
