package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.*;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.savable.CustomizeSavableAction;
import com.epam.lab.spider.model.db.service.savable.SavableService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class TaskService implements BaseService<Task>, SavableService<Task> {

    private static DAOFactory factory = DAOFactory.getInstance();
    private TaskDAO tdao = factory.create(TaskDAO.class);
    private TaskSourceDAO tsdao = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO tddao = factory.create(TaskDestinationDAO.class);
//    private CategoryHasTaskDAO chtdao = factory.create(CategoryHasTaskDAO.class);
    private FilterDAO fdao = factory.create(FilterDAO.class);

    @Override
    public boolean save(Task entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Task entity, final Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.customSave(conn, entity, new Object[]{entity.getFilter()},null, new CustomizeSavableAction[]{
            new CustomizeSavableAction() {
                @Override
                public void action(Object entity) throws SQLException {
                    Task task = (Task) entity;
                    tsdao.deleteByTaskId(conn, task.getId());
                    for (Wall wall : task.getSource()) {
                        assertTransaction(tsdao.insert(conn, task.getId(), wall.getId()));
                    }
                    tddao.deleteByTaskId(conn, task.getId());
                    for (Wall wall : task.getDestination()) {
                        assertTransaction(tddao.insert(conn, task.getId(), wall.getId()));
                    }
                }
            }
        });
    }

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
                //приймаємо до уваги що джерел може небути
                tsdao.deleteByTaskId(connection, id);
                for (Wall wall : task.getSource()) {
                    assertTransaction(tsdao.insert(connection, id, wall.getId()));
                }
                //приймаємо до уваги що призначень може небути
                tddao.deleteByTaskId(connection, id);
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
//                assertTransaction(chtdao.deleteByTaskId(connection, id));
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

    public Task getByIdAndLimitByUserId(int id, int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByIdLimitByUserId(connection, id, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getRunnableByNextRunDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getAllByNextRunDateLimitByState(connection, date, Task.State.RUNNING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateTimeToNextRun(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.updateNextTimeRun(connection, task.getId(), task.getNextTaskRunDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateNextTimeRunAndState(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.updateNextTimeRunAndState(connection, task.getId(), task.getNextTaskRunDate(), task.getState());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateState(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.updateState(connection, task.getId(), task.getState());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Task> getByCategoryId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getByCategoryId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        try (Connection connection = PoolConnection.getConnection()) {
            return tdao.getCount(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
