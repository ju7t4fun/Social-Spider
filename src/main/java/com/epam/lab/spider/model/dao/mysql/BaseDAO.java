package com.epam.lab.spider.model.dao.mysql;

import java.sql.*;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 11.06.2015.
 */
public abstract class BaseDAO {

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
            if (arg.getClass() == String.class ) {
                statement.setString(i, arg.toString());
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
                statement.setDate(i, new Date(((java.util.Date) arg).getTime()));
            }
        }
        return statement;
    }

}
