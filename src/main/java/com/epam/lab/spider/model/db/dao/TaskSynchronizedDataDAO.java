package com.epam.lab.spider.model.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by shell on 6/16/2015.
 */
public interface TaskSynchronizedDataDAO {

    public Integer getIdLastProcessedPost(Connection connection, Integer taskId, Integer wallId) throws SQLException;

    public Set<Integer> getProcessedPost(Connection connection, Integer taskId, Integer wallId, int limit) throws
            SQLException;

    public boolean checkIdProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer wallId)
            throws SQLException;

    public boolean markIdLastProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer
            wallId) throws SQLException;

    public boolean clearOldData(Connection connection) throws SQLException;

}
