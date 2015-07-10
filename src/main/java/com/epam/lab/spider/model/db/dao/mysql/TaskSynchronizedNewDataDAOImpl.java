package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.entity.SynchronizedData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class TaskSynchronizedNewDataDAOImpl extends BaseDAO implements TaskSynchronizedNewDataDAO {
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS task_synchronized_new_data ( " +
            "task_id INT NOT NULL,  wall_id INT NOT NULL,  " +
            "offset INT NOT NULL,  vk_inner_post_id INT NOT NULL,  " +
            "PRIMARY KEY (task_id, wall_id)) ENGINE = InnoDB;";
    private static final String SQL_SELECT = "SELECT * FROM task_synchronized_new_data WHERE task_id=? and wall_id=?";
    private static final String SQL_INSERT = "INSERT INTO task_synchronized_new_data (task_id, wall_id,offset, vk_inner_post_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE task_synchronized_new_data SET offset=?, vk_inner_post_id=? WHERE task_id=? and wall_id=?";
    private static final String SQL_DELETE = "DELETE FROM task_synchronized_new_data WHERE task_id=? and wall_id=?";
    @Override
    public  boolean createTable(Connection conn) throws SQLException {
        return changeQuery(conn,SQL_CREATE);
    }
    @Override
    public boolean insert(Connection connection,  Integer taskId, Integer wallId, Integer offset, Integer vkInnerPostId) throws SQLException {
        return changeQuery(connection,SQL_INSERT, taskId, wallId, offset, vkInnerPostId);
    }
    @Override
    public boolean update(Connection connection,  Integer taskId, Integer wallId, Integer offset, Integer vkInnerPostId) throws SQLException {
        return changeQuery(connection,SQL_UPDATE, offset, vkInnerPostId, taskId, wallId);
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
            synchronizedData = new SynchronizedData();
            synchronizedData.setPostVkId(rs.getInt("vk_inner_post_id"));
            synchronizedData.setPostOffset(rs.getInt("vk_inner_post_id"));
            synchronizedData.setTaskId(rs.getInt("task_id"));
            synchronizedData.setWallId(rs.getInt("wall_id"));
            synchronizedDatas.add(synchronizedData);
        }
        return synchronizedDatas;
    }
}
