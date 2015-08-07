package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.persistence.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.entity.SynchronizedData;
import com.epam.lab.spider.persistence.factory.SynchronizedDataAbstractFactory;
import com.epam.lab.spider.persistence.factory.SynchronizedDataFactoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kovalik
 */
public class TaskSynchronizedNewDataDAOImpl extends BaseDAO implements TaskSynchronizedNewDataDAO<SynchronizedData> {
    private static final SynchronizedDataAbstractFactory<SynchronizedData> FACTORY = new SynchronizedDataFactoryImpl();
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS task_synchronized_new_data ( " +
            "task_id INT NOT NULL,  wall_id INT NOT NULL,  " +
            "offset INT NOT NULL,  vk_inner_post_id INT NOT NULL,  " +
            "PRIMARY KEY (task_id, wall_id)) ENGINE = InnoDB;";
    private static final String SQL_SELECT = "SELECT * FROM task_synchronized_new_data WHERE task_id=? and wall_id=?";
    private static final String SQL_INSERT = "INSERT INTO task_synchronized_new_data (task_id, wall_id,offset, vk_inner_post_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE task_synchronized_new_data SET offset=?, vk_inner_post_id=? WHERE task_id=? and wall_id=?";
    private static final String SQL_DELETE = "DELETE FROM task_synchronized_new_data WHERE task_id=? and wall_id=?";

    public TaskSynchronizedNewDataDAOImpl() {
    }

    @Override
    public  boolean createTable(Connection conn) throws SQLException {
        return changeQuery(conn,SQL_CREATE);
    }
    @Override
    public boolean insert(Connection connection,  SynchronizedData sync) throws SQLException {
        return changeQuery(connection,SQL_INSERT, sync.getTaskId(), sync.getWallId(), sync.getPostOffset(), sync.getPostVkId());
    }
    @Override
    public boolean update(Connection connection,  SynchronizedData sync) throws SQLException {
        return changeQuery(connection,SQL_UPDATE, sync.getPostOffset(), sync.getPostVkId(), sync.getTaskId(), sync.getWallId());
    }
    @Override
    public boolean delete(Connection connection,  Integer taskId, Integer wallId) throws SQLException {
        return changeQuery(connection,SQL_DELETE, taskId, wallId);
    }
    @Override
    public SynchronizedData getById(Connection connection,  Integer taskId, Integer wallId) throws SQLException {
        return first(select(connection, SQL_SELECT, taskId, wallId));
    }

    public List<SynchronizedData> select(Connection connection, String query, Object... args) throws SQLException {
        List<SynchronizedData> synchronizedDatas = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        SynchronizedData synchronizedData;
        while (rs.next()) {
            synchronizedData =  FACTORY.createSynchronizedData();
            synchronizedData.setPostVkId(rs.getInt("vk_inner_post_id"));
            synchronizedData.setPostOffset(rs.getInt("offset"));
            synchronizedData.setTaskId(rs.getInt("task_id"));
            synchronizedData.setWallId(rs.getInt("wall_id"));
            synchronizedDatas.add(synchronizedData);
        }
        return synchronizedDatas;
    }
}
