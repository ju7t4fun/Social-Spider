package com.epam.lab.spider.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public interface TaskSynchronizedDataDAO {

    Integer getIdLastProcessedPost(Connection connection, Integer taskId, Integer wallId) throws SQLException;

    Set<Integer> getProcessedPost(Connection connection, Integer taskId, Integer wallId, int limit) throws
            SQLException;

    boolean checkIdProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer wallId)
            throws SQLException;

    boolean markIdLastProcessedPost(Connection connection, Integer vkInnerPostId, Integer taskId, Integer
            wallId) throws SQLException;

    boolean clearOldData(Connection connection) throws SQLException;

    Map<Long, Integer> statisticsExecution(Connection connection, String date) throws SQLException;

    boolean deleteByTaskId(Connection connection, int taskId) throws SQLException;

    boolean deleteByWallId(Connection connection, int wallId) throws SQLException;

}
