package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.entity.SynchronizedDataAuditable;
import com.epam.lab.spider.persistence.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.persistence.factory.SynchronizedDataAbstractFactory;
import com.epam.lab.spider.persistence.factory.SynchronizedDataAuditableFactoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kovalik
 */
public class TaskSynchronizedNewDataAuditableDAOImpl extends BaseDAO implements TaskSynchronizedNewDataDAO<SynchronizedDataAuditable> {
    private static final SynchronizedDataAbstractFactory<SynchronizedDataAuditable> FACTORY = new SynchronizedDataAuditableFactoryImpl();
    private static final String TABLE_NAME = "task_synchronized_new_data_auditable";
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
            "task_id INT NOT NULL,  wall_id INT NOT NULL,  " +
            "offset INT NOT NULL,  vk_inner_post_id INT NOT NULL,  " +
            "audit_rev INT NOT NULL DEFAULT 0, " +
            "PRIMARY KEY (task_id, wall_id,audit_rev)) ENGINE = InnoDB;";
    private static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME + " WHERE task_id=? and wall_id=? ORDER BY audit_rev DESC LIMIT 1";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (task_id, wall_id, audit_rev, offset, vk_inner_post_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE task_id=? and wall_id=?";

    @Override
    public boolean createTable(Connection conn) throws SQLException {
        return changeQuery(conn, SQL_CREATE);
    }

    @Override
    public boolean insert(Connection connection, SynchronizedDataAuditable sync) throws SQLException {
        return changeQuery(connection, SQL_INSERT, sync.getTaskId(), sync.getWallId(), sync.getRevisionCount(), sync.getPostOffset(), sync.getPostVkId());
    }

    @Override
    public boolean update(Connection connection, SynchronizedDataAuditable sync) throws SQLException {
        return changeQuery(connection, SQL_INSERT, sync.getTaskId(), sync.getWallId(), sync.getRevisionCount() + 1, sync.getPostOffset(), sync.getPostVkId());
    }

    @Override
    public boolean delete(Connection connection, Integer taskId, Integer wallId) throws SQLException {
        return changeQuery(connection, SQL_DELETE, taskId, wallId);
    }

    @Override
    public SynchronizedDataAuditable getById(Connection connection, Integer taskId, Integer wallId) throws SQLException {
        return first(select(connection, SQL_SELECT, taskId, wallId));
    }

    public List<SynchronizedDataAuditable> select(Connection connection, String query, Object... args) throws SQLException {
        List<SynchronizedDataAuditable> synchronizedDatas = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        SynchronizedDataAuditable synchronizedData;
        while (rs.next()) {
            synchronizedData = FACTORY.createSynchronizedData();
            synchronizedData.setPostVkId(rs.getInt("vk_inner_post_id"));
            synchronizedData.setPostOffset(rs.getInt("offset"));
            synchronizedData.setTaskId(rs.getInt("task_id"));
            synchronizedData.setWallId(rs.getInt("wall_id"));
            synchronizedData.setRevisionCount(rs.getInt("audit_rev"));
            synchronizedDatas.add(synchronizedData);
        }
        return synchronizedDatas;
    }
}
