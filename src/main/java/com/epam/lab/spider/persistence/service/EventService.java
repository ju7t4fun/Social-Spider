package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.EventDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.entity.Event;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
public class EventService implements BaseService<Event> {
    private static final Logger LOG = Logger.getLogger(EventService.class);

    private static DAOFactory factory = DAOFactory.getInstance();
    private static EventDAO eventDAO = factory.create(EventDAO.class);

    @Override
    public boolean insert(Event event) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.insert(connection, event);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Event event) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.update(connection, id, event);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.delete(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public List<Event> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Event getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Event> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Event> getByShownUserId(int clientId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getByShownUserId(connection, clientId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean markAsShowByUserId(int clientId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.markAsShowByUserId(connection, clientId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public int getCountUnShownNotificationByUserId(int id) {
        return getByShownUserId(id).size();
    }

    public List<Event> getLastUnShownByUserId(int id, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getLastUnShownByUserId(connection, id, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Event> getByUserId(Integer id, int page, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getByUserId(connection, id, page, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.getCountByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public Map<Long, Integer> statisticsError(String date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return eventDAO.statisticsExecution(connection, date);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
