package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.MessageDAO;
import com.epam.lab.spider.model.db.entity.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 23.06.2015.
 */
public class MessageDAOImp extends BaseDAO implements MessageDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO message (user_id, text, type, deleted, is_read) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE message SET user_id = ?, text = ?, date = ?, type " +
            "= ?, deleted = ?, id_read = ? WHERE id = ?";
    private static final String SQL_DELETED_QUERY = "UPDATE message SET deleted = ? WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM message";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM message WHERE id = ?";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM message WHERE user_id = ? AND deleted = 0";
    private static final String SQL_GET_BY_USER_ID_LIMIT_QUERY = "SELECT * FROM message WHERE user_id = ? AND deleted" +
            " = 0 ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_GET_COUNT_UN_READ_BY_USER_ID_QUERY = "SELECT * FROM message WHERE user_id = ? AND" +
            " deleted = 0 AND is_read = 0 AND type = 'to_user'";
    private static final String SQL_MARK_AS_READ_BY_USER_ID_QUERY = "UPDATE message SET is_read = 1 WHERE user_id = ?" +
            " AND type = 'to_user'";
    private static final String SQL_GET_LIST_UN_READ_ADMIN_MESSAGES_QUERY = "SELECT user_id, COUNT(*) FROM message " +
            "WHERE type = 'TO_ADMIN' AND is_read = 0 AND deleted = 0 GROUP BY user_id LIMIT ?";
    private static final String SQL_GET_COUNT_UN_READ_BY_ADMIN_ID_QUERY = "SELECT * FROM message WHERE is_read = 0 " +
            "AND type = 'TO_ADMIN'";
    private static final String SQL_GET_LAST_UN_READ_MESSAGES_BY_ADMIN_QUERY = "SELECT * FROM message WHERE type = " +
            "'TO_ADMIN' AND is_read = 0 ORDER BY date DESC LIMIT ?";

    @Override
    public boolean insert(Connection connection, Message message) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                message.getUserId(),
                message.getText(),
                message.getType().toString(),
                message.getDeleted(),
                message.getRead());
        message.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Message message) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                message.getUserId(),
                message.getText(),
                message.getDate(),
                message.getType().toString().toUpperCase(),
                message.getDeleted(),
                message.getRead(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETED_QUERY, id);
    }

    @Override
    public List<Message> select(Connection connection, String query, Object... args) throws SQLException {
        List<Message> messages = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Message message;
        while (rs.next()) {
            message = new Message();
            message.setId(rs.getInt("id"));
            message.setUserId(rs.getInt("user_id"));
            message.setText(rs.getString("text"));
            message.setDate(rs.getTimestamp("date"));
            message.setType(Message.Type.valueOf(rs.getString("type").toUpperCase()));
            message.setDeleted(rs.getBoolean("deleted"));
            message.setRead(rs.getBoolean("is_read"));
            messages.add(message);
        }
        return messages;
    }

    @Override
    public List<Message> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Message getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Message> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

    @Override
    public List<Message> getByUserId(Connection connection, int userId, int offset, int limit) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_LIMIT_QUERY, userId, offset, limit);
    }

    @Override
    public int getCountUnReadByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_COUNT_UN_READ_BY_USER_ID_QUERY, userId).size();
    }

    @Override
    public boolean markAsReadByUserId(Connection connection, int userId) throws SQLException {
        return changeQuery(connection, SQL_MARK_AS_READ_BY_USER_ID_QUERY, userId);
    }

    @Override
    public Map<Integer, Integer> getListUnReadAdminMessages(Connection connection, int limit) throws SQLException {
        Map<Integer, Integer> userCountUnRead = new HashMap<>();
        ResultSet rs = selectQuery(connection, SQL_GET_LIST_UN_READ_ADMIN_MESSAGES_QUERY, limit);
        while (rs.next()) {
            userCountUnRead.put(rs.getInt("user_id"), rs.getInt("COUNT(*)"));
        }
        return userCountUnRead;
    }

    @Override
    public int getCountUnReadByAdminId(Connection connection) throws SQLException {
        return select(connection, SQL_GET_COUNT_UN_READ_BY_ADMIN_ID_QUERY).size();
    }

    @Override
    public List<Message> getLastUnReadMessagesByAdmin(Connection connection, int limit) throws SQLException {
        return select(connection, SQL_GET_LAST_UN_READ_MESSAGES_BY_ADMIN_QUERY, limit);
    }

}
