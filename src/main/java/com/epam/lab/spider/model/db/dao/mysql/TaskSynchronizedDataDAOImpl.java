package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.TaskSynchronizedDataDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shell on 6/16/2015.
 */
public class TaskSynchronizedDataDAOImpl extends BaseDAO implements TaskSynchronizedDataDAO {

    private static final String SQL_GET_VK_INNER_POST_ID = "SELECT DISTINCT vk_inner_post_id FROM task_synchronized_data " +
            "WHERE task_id = ? AND wall_id = ? ORDER BY processing_number DESC LIMIT ?;";

    private static final String SQL_SET_VK_INNER_POST_ID = "INSERT INTO task_synchronized_data (task_id, wall_id, vk_inner_post_id) VALUES (?, ?, ?)";
    private static final String SQL_CHECK_VK_INNER_POST_ID = "SELECT count(*)>0 FROM task_synchronized_data WHERE vk_inner_post_id = 1 AND task_id = 1 AND wall_id = 1";


    @Override
    public Integer getIdLastProcessedPost(Connection connection, Integer taskId, Integer wallId) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_VK_INNER_POST_ID, taskId,wallId,1);
        if(rs.next()){
            return rs.getInt(1);
        }
        else  return null;
    }

    @Override
    public Set<Integer> getProcessedPost(Connection connection, Integer taskId, Integer wallId, int limit) throws SQLException {
        Set<Integer> set = new HashSet<>();
        ResultSet rs = selectQuery(connection, SQL_GET_VK_INNER_POST_ID, taskId,wallId,limit);
        while(rs.next()){

            set.add(rs.getInt(1));
        }
        return set;
    }

    @Override
    public boolean checkIdProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer wallId) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_CHECK_VK_INNER_POST_ID, vkInnerPostId, taskId,wallId);
        if(rs.next()){
            return rs.getBoolean(1);
        }
        return false;
    }

    @Override
    public boolean markIdLastProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer wallId) throws SQLException {
        return changeQuery(connection,SQL_SET_VK_INNER_POST_ID,taskId, wallId, vkInnerPostId);
    }

    @Override
    public boolean clearOldData(Connection connection) throws SQLException {
        return false;
    }
}
