package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.PersistenceIdentificationChangeable;
import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.impl.persistence.PersistenceEntityFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public abstract class BaseDAO {
    public static final Logger LOG = Logger.getLogger(Object.class);

    protected static final AbstractEntityFactory ENTITY_FACTORY = PersistenceEntityFactory.getInstance();

    private static final String SQL_GET_LAST_INSERT_ID_QUERY = "SELECT LAST_INSERT_ID()";

    public static <T> boolean setId(T entity, Integer id) {
        if (entity instanceof PersistenceIdentificationChangeable) {
            PersistenceIdentificationChangeable changeable = (PersistenceIdentificationChangeable) entity;
            changeable.setId(id);
            return true;
        } else {
            LOG.warn("Can not set id, because " + entity.getClass() + " not implements 'PersistenceIdentificationChangeable'.");
            return false;
        }
    }

    @Deprecated
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
        PreparedStatement statement;
        ResultSet rs = null;
        try {
            statement = buildPreparedStatement(connection, query, args);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Выполнение SQL запроса : " + statement.toString().substring(48) + ";");
            }
            rs = statement.executeQuery();
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return rs;
    }


    protected Integer insertQuery(Connection connection, String query, Object... args) throws SQLException {
        int insertRow;
        Integer lastInsertId = null;
        PreparedStatement statement = null;
        try {
            statement = buildPreparedStatement(connection, query, true, args);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Выполнение SQL запроса : " + statement.toString().substring(48) + ";");
            }
            insertRow = statement.executeUpdate();
            if (insertRow > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    lastInsertId = generatedKeys.getInt(1);
                }

            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        } finally {
            if (statement != null)
                statement.close();
        }
        return lastInsertId;
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
            LOG.error(e.getLocalizedMessage(), e);
        } finally {
            if (statement != null)
                statement.close();
        }
        return insertRow > 0;
    }

    private PreparedStatement buildPreparedStatement(Connection connection, String query, Object... args) throws
            SQLException {
        return buildPreparedStatement(connection, query, false, args);
    }

    private PreparedStatement buildPreparedStatement(Connection connection, String query, boolean isNeedReturnKeys, Object... args) throws
            SQLException {
        PreparedStatement statement;
        if (isNeedReturnKeys) {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            statement = connection.prepareStatement(query);
        }
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
            if (arg.getClass() == java.util.Date.class ) {
                statement.setTimestamp(i, new Timestamp(((java.util.Date) arg).getTime()));
                continue;
            }
            if (arg.getClass() == Timestamp.class) {
                statement.setTimestamp(i, (Timestamp) arg);
                continue;
            }
            else{
                statement.setObject(i, arg.toString());
                LOG.error("Объект класса \"" + arg.getClass().getName() + "\" не установлен в PreparedStatement");
                continue;
            }
        }
        return statement;
    }

}
