package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.SynchronizedData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hell-engine on 7/10/2015.
 */
public interface TaskSynchronizedNewDataDAO {
    public  boolean createTable(Connection conn) throws SQLException;

    public boolean insert(Connection connection,  Integer taskId, Integer wallId, Integer offset, Integer vkInnerPostId) throws SQLException ;

    public boolean update(Connection connection,  Integer taskId, Integer wallId, Integer offset, Integer vkInnerPostId) throws SQLException ;

    public boolean delete(Connection connection,  Integer taskId, Integer wallId) throws SQLException;

    public SynchronizedData getById(Connection connection,  Integer taskId, Integer wallId) throws SQLException;
}
