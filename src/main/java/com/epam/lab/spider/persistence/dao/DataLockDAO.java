package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.DataLock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public interface DataLockDAO {
    List<DataLock> dataLockByUserId(Connection conn, Integer eventID) throws SQLException;
    Map<String, Map<Integer, Set<DataLock.Mode>>> restore(Connection conn) throws SQLException ;
    boolean createLock(Connection conn, String table, Integer index, DataLock.Mode mode, Integer userId) throws SQLException ;
    boolean deleteLock(Connection conn, String table, Integer index, DataLock.Mode mode) throws SQLException;
}
