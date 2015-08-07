package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Message;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.MessageDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
public class MessageService implements BaseService<Message> {
    private static final Logger LOG = Logger.getLogger(MessageService.class);

    private static DAOFactory factory = DAOFactory.getInstance();
    private static MessageDAO messageDAO = factory.create(MessageDAO.class);

    @Override
    public boolean insert(Message message) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.insert(connection, message);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Message message) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.update(connection, id, message);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.delete(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Message getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Message> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Message> getByUserId(int userId, int offset, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getByUserId(connection, userId, offset, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountUnReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getCountUnReadByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public boolean markAsReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.markAsReadByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public Map<Integer, Integer> getListUnReadAdminMessages(int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getListUnReadAdminMessages(connection, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountUnReadByAdminId() {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getCountUnReadByAdminId(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public List<Message> getLastUnReadMessagesByAdmin(int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.getLastUnReadMessagesByAdmin(connection, limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean markAsReadAdminByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return messageDAO.markAsReadAdminByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
