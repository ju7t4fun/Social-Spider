package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.EventDAO;
import com.epam.lab.spider.model.db.entity.Event;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class EventDAOImp extends BaseDAO implements EventDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO event (user_id, type, title, message, shown)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE event SET user_id = ?, type = ?, title = ? message = ?, " +
            "shown = ?, time = ? WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM event WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM event";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM event WHERE id = ?";
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM event WHERE user_id = ?";
    private static final String SQL_GET_BY_SHOWN_USER_ID_QUERY = "SELECT * FROM event WHERE user_id = ? AND shown = 0";
    private static final String SQL_MARK_AS_SHOW_BY_USER_ID_QUERY = "UPDATE event SET shown = 1 WHERE user_id = ?";
    private static final String SQL_GET_LAST_UN_SHOWN_BY_USER_ID_QUERY = "SELECT * FROM event WHERE user_id = ? " +
            "AND shown = 0 ORDER BY time DESC LIMIT ?";

    @Override
    public boolean insert(Connection connection, Event event) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getTitle(),
                event.getMessage(),
                event.getShown());
        event.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Event event) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getTitle(),
                event.getMessage(),
                event.getShown(),
                event.getTitle(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Event> select(Connection connection, String query, Object... args) throws SQLException {
        List<Event> events = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Event event;
        while (rs.next()) {
            event = new Event();
            event.setId(rs.getInt("id"));
            event.setUserId(rs.getInt("user_id"));
            event.setType(Event.Type.valueOf(rs.getString("type").toUpperCase()));
            event.setTime(rs.getTimestamp("time"));
            event.setMessage(rs.getString("message"));
            event.setTitle(rs.getString("title"));
            event.setShown(rs.getBoolean("shown"));
            events.add(event);
        }
        return events;
    }

    @Override
    public List<Event> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Event getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Event> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID, userId);
    }

    @Override
    public List<Event> getByShownUserId(Connection connection, int clientId) throws SQLException {
        return select(connection, SQL_GET_BY_SHOWN_USER_ID_QUERY, clientId);
    }

    @Override
    public boolean markAsShowByUserId(Connection connection, int clientId) throws SQLException {
        return changeQuery(connection, SQL_MARK_AS_SHOW_BY_USER_ID_QUERY, clientId);
    }

    @Override
    public List<Event> getLastUnShownByUserId(Connection connection, int id, int limit) throws SQLException {
        return select(connection, SQL_GET_LAST_UN_SHOWN_BY_USER_ID_QUERY, id, limit);
    }
}
