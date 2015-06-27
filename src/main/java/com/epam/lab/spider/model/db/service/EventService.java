package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.EventDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class EventService implements BaseService<Event> {

    private static final DAOFactory factory = DAOFactory.getInstance();
    private static final EventDAO edao = factory.create(EventDAO.class);

    @Override
    public boolean insert(Event event) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.insert(connection, event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Event event) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.update(connection, id, event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Event> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Event getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getByShownUserId(int clientId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getByShownUserId(connection, clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean markAsShowByUserId(int clientId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.markAsShowByUserId(connection, clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCountUnShownNotificationByUserId(int id) {
        return getByShownUserId(id).size();
    }

    public List<Event> getLastUnShownByUserId(int id, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getLastUnShownByUserId(connection, id, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> getByUserId(Integer id, int page, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return edao.getByUserId(connection, id, page, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
