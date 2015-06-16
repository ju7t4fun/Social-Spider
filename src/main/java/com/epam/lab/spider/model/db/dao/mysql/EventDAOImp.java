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

    private static final String SQL_INSERT_QUERY = "INSERT INTO event (user_id, type, message, deleted)" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE event SET user_id = ?, type = ?, message = ?, deleted = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE event SET deleted = false WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM event WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM event WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM event WHERE user_id = ? AND deleted = false";

    @Override
    public boolean insert(Connection connection, Event event) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getMessage(),
                event.getDeleted());
        event.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Event event) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getMessage(),
                event.getDeleted(),
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
            event.setDeleted(rs.getBoolean("deleted"));
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
}
