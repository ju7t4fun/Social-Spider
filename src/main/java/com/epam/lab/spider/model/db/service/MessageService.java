package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.MessageDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 23.06.2015.
 */
public class MessageService implements BaseService<Message> {

    private static DAOFactory factory = DAOFactory.getInstance();
    private static MessageDAO mdao = factory.create(MessageDAO.class);

    @Override
    public boolean insert(Message message) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.insert(connection, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Message message) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.update(connection, id, message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getByUserId(int userId, int offset, int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getByUserId(connection, userId, offset, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountUnReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getCountUnReadByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean markAsReadByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.markAsReadByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Integer> getListUnReadAdminMessages(int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getListUnReadAdminMessages(connection, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountUnReadByAdminId() {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getCountUnReadByAdminId(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Message> getLastUnReadMessagesByAdmin(int limit) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.getLastUnReadMessagesByAdmin(connection, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean markAsReadAdminByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return mdao.markAsReadAdminByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
