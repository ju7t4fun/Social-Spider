package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.model.entity.CategoryHasTask;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.*;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.CustomizeSavableAction;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static com.epam.lab.spider.persistence.SQLTransactionException.assertTransaction;

/**
 * @author Boyarsky Vitaliy
 */
public class TaskService implements BaseService<Task>, SavableService<Task> {
    private static final Logger LOG = Logger.getLogger(TaskService.class);

    private static DAOFactory factory = DAOFactory.getInstance();
    private TaskDAO taskDAO = factory.create(TaskDAO.class);
    private TaskSourceDAO sourceDAO = factory.create(TaskSourceDAO.class);
    private TaskDestinationDAO destinationDAO = factory.create(TaskDestinationDAO.class);
    private CategoryHasTaskDAO categoryHasTaskDAO = factory.create(CategoryHasTaskDAO.class);
    private TaskSynchronizedDataDAO taskSynchronizedDataDAO = factory.create(TaskSynchronizedDataDAO.class);
    private FilterDAO filterDAO = factory.create(FilterDAO.class);

    @Override
    public boolean save(Task entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Task entity, final Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.customSave(conn, entity, new PersistenceIdentifiable[]{entity.getFilter()}, null, new CustomizeSavableAction[]{
                new CustomizeSavableAction() {
                    @Override
                    public void action(Object entity) throws SQLException {
                        Task task = (Task) entity;
                        sourceDAO.deleteByTaskId(conn, task.getId());
                        for (Wall wall : task.getSource()) {
                            assertTransaction(sourceDAO.insert(conn, task.getId(), wall.getId()));
                        }
                        destinationDAO.deleteByTaskId(conn, task.getId());
                        for (Wall wall : task.getDestination()) {
                            assertTransaction(destinationDAO.insert(conn, task.getId(), wall.getId()));
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
                assertTransaction(taskDAO.insert(connection, task));
                assertTransaction(filterDAO.insert(connection, task.getFilter()));
                for (Wall wall : task.getSource()) {
                    assertTransaction(sourceDAO.insert(connection, task.getId(), wall.getId()));
                }
                for (Wall wall : task.getDestination()) {
                    assertTransaction(destinationDAO.insert(connection, task.getId(), wall.getId()));
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public boolean update(int id, Task task) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(taskDAO.update(connection, id, task));
                assertTransaction(filterDAO.update(connection, task.getFilterId(), task.getFilter()));
                //приймаємо до уваги що джерел може небути
                sourceDAO.deleteByTaskId(connection, id);
                for (Wall wall : task.getSource()) {
                    assertTransaction(sourceDAO.insert(connection, id, wall.getId()));
                }
                //приймаємо до уваги що призначень може небути
                destinationDAO.deleteByTaskId(connection, id);
                for (Wall wall : task.getDestination()) {
                    assertTransaction(destinationDAO.insert(connection, id, wall.getId()));
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                destinationDAO.deleteByTaskId(connection, id);
                sourceDAO.deleteByTaskId(connection, id);
                categoryHasTaskDAO.deleteByTaskId(connection, id);
                taskSynchronizedDataDAO.deleteByTaskId(connection, id);
                taskDAO.delete(connection, id);
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
    public List<Task> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Task getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Task> getByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public Task getByIdAndLimitByUserId(int id, int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getByIdLimitByUserId(connection, id, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


    public Task getByIdNoLimit(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getByIdAdminRules(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Task> getRunnableByNextRunDate(Date date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getAllByNextRunDateLimitByState(connection, date, Task.State.RUNNING);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean updateTimeToNextRun(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.updateNextTimeRun(connection, task.getId(), task.getNextTaskRunDate());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateNextTimeRunAndState(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.updateNextTimeRunAndState(connection, task.getId(), task.getNextTaskRunDate(), task.getState());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateState(Task task) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.updateState(connection, task.getId(), task.getState());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }


    public List<Task> getByCategoryId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getByCategoryId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Task> getAllLimited(int userId, int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getAllLimited(connection, userId, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCount(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getCount(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Task> getAllActiveLimited(int userId, int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getAllActiveLimited(connection, userId, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getActiveCount(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getActiveCount(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Task> getAllLimitedAdmin(int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getAllLimitedAdmin(connection, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountAdmin() {
        try (Connection connection = PoolConnection.getConnection()) {
            return taskDAO.getCountAdmin(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public boolean deleteFromCHT(int categoryId, int taskId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryHasTaskDAO.delete(connection, categoryId, taskId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    public boolean insertIntoCHT(int categoryId, int taskId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryHasTaskDAO.insert(connection, categoryId, taskId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    public List<CategoryHasTask> getAllCHTByTaskId(int taskId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return categoryHasTaskDAO.getAllCHTByTaskId(connection, taskId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
