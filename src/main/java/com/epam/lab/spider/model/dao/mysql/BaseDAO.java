package com.epam.lab.spider.model.dao.mysql;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 11.06.2015.
 */
public abstract class BaseDAO {

    private static final Logger LOG = Logger.getLogger(BaseDAO.class);

    private static final String SQL_GET_LAST_INSERT_ID_QUERY = "SELECT LAST_INSERT_ID()";

    public int getLastInsertId(Connection connection) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_LAST_INSERT_ID_QUERY);
        if (rs.next()) {
            return rs.getInt("LAST_INSERT_ID()");
        }
        return 0;
    }

    public <T> T first(List<T> list) {
        try {
            return list.get(0);
        } catch (IndexOutOfBoundsException ignored) {
        }
        return null;
    }

    protected ResultSet selectQuery(Connection connection, String query, Object... args) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = buildPreparedStatement(connection, query, args);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Выполнение SQL запроса : " + statement.toString().substring(48) + ";");
            }
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    protected boolean changeQuery(Connection connection, String query, Object... args) throws SQLException {
        int insertRow = 0;
        PreparedStatement statement = null;
        try {
            statement = buildPreparedStatement(connection, query, args);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Выполнение SQL запроса : " + statement.toString().substring(48) + ";");
            }
            insertRow = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                statement.close();
        }
        return insertRow > 0;
    }

    private PreparedStatement buildPreparedStatement(Connection connection, String query, Object... args) throws
            SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement(query);
        for (int i = 1; i < args.length + 1; i++) {
            Object arg = args[i - 1];
            if (arg == null) {
                statement.setNull(i, Types.NULL);
                continue;
            }
            if (arg.getClass() == String.class) {
                statement.setString(i, (String) arg);
                continue;
            }
            if (arg.getClass() == Integer.class) {
                statement.setInt(i, (Integer) arg);
                continue;
            }
            if (arg.getClass() == Boolean.class) {
                statement.setBoolean(i, (Boolean) arg);
                continue;
            }
            if (arg.getClass() == Long.class) {
                statement.setLong(i, (Long) arg);
                continue;
            }
            if (arg.getClass() == java.util.Date.class) {
                statement.setTimestamp(i, new Timestamp(((java.util.Date) arg).getTime()));
                continue;
            }
            LOG.error("Объект класса \"" + arg.getClass().getName() + "\" не установлен в PreparedStatement");
        }
        return statement;
    }

}
