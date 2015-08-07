package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.SynchronizedWrapperUtils;
import com.epam.lab.spider.model.entity.Event;
import com.epam.lab.spider.model.entity.impl.EventImpl;
import com.epam.lab.spider.persistence.dao.EventDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
public class EventDAOImp extends BaseDAO implements EventDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO event (user_id, type, title, message, shown)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE event SET user_id = ?, type = ?, title = ?, message = ?, " +
            "shown = ?, time = ? WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM event WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM event";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM event WHERE id = ?";
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM event WHERE user_id = ?";
    private static final String SQL_GET_BY_SHOWN_USER_ID_QUERY = "SELECT * FROM event WHERE user_id = ? AND shown = 0";
    private static final String SQL_MARK_AS_SHOW_BY_USER_ID_QUERY = "UPDATE event SET shown = 1 WHERE user_id = ?";
    private static final String SQL_GET_LAST_UN_SHOWN_BY_USER_ID_QUERY = "SELECT * FROM event WHERE user_id = ? " +
            "AND shown = 0 ORDER BY time DESC LIMIT ?";
    private static final String SQL_GET_BY_USER_ID_PAGE_QUERY = "SELECT * FROM event WHERE user_id = ? ORDER BY time " +
            "DESC LIMIT ?,?";
    private static final String GET_COUNT_BY_USER_ID_QUERY = "SELECT COUNT(*) FROM event WHERE user_id = ?";
    private static final String SQL_STATISTICS_ERROR_QUERY = "SELECT COUNT(*) AS count, DATE_FORMAT(time, '%Y-%m-%d " +
            "%H') AS date FROM event WHERE type = 'ERROR' AND time > ? AND time <= ? GROUP BY UNIX_TIMESTAMP(time) " +
            "DIV 3600;";

    @Override
    public boolean insert(Connection connection, Event event) throws SQLException {
        Integer newId = insertQuery(connection, SQL_INSERT_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getTitle(),
                event.getMessage(),
                event.getShown());
        setId(event, newId);
        return newId != null;
    }

    @Override
    public boolean update(Connection connection, int id, Event event) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                event.getUserId(),
                event.getType().toString().toUpperCase(),
                event.getTitle(),
                event.getMessage(),
                event.getShown(),
                event.getTime(),
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
            event = ENTITY_FACTORY.createEvent();
            setId(event, rs.getInt("id"));
            event.setUserId(rs.getInt("user_id"));
            event.setType(EventImpl.Type.valueOf(rs.getString("type").toUpperCase()));
            event.setTime(rs.getTimestamp("time"));
            event.setMessage(rs.getString("message"));
            event.setTitle(rs.getString("title"));
            event.setShown(rs.getBoolean("shown"));
            events.add(SynchronizedWrapperUtils.wrap(event));
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

    @Override
    public List<Event> getByUserId(Connection connection, Integer id, int page, int limit) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_PAGE_QUERY, id, page, limit);
    }

    @Override
    public int getCountByUserId(Connection connection, Integer id) throws SQLException {
        ResultSet rs = selectQuery(connection, GET_COUNT_BY_USER_ID_QUERY, id);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return 0;
    }

    @Override
    public Map<Long, Integer> statisticsExecution(Connection connection, String date) throws SQLException {
        String fromDate = date + " 00:00:00";
        String toDate = date + "23:59:59";
        ResultSet rs = selectQuery(connection, SQL_STATISTICS_ERROR_QUERY, fromDate, toDate);
        Map<Long, Integer> statistics = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (rs.next()) {
            String format = rs.getString("date") + ":00:00";
            try {
                statistics.put(dateFormat.parse(format).getTime(), rs.getInt("count"));
            } catch (ParseException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
        return statistics;
    }
}
