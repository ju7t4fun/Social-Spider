package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.DataLock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shell on 6/20/2015.
 */
public interface DataLockDAO {
    List<DataLock> dataLockByUserId(Connection conn, Integer eventID) throws SQLException;
    Map<String, Map<Integer, Set<DataLock.Mode>>> restore(Connection conn) throws SQLException ;
    boolean createLock(Connection conn, String table, Integer index, DataLock.Mode mode, Integer userId) throws SQLException ;
    boolean deleteLock(Connection conn, String table, Integer index, DataLock.Mode mode) throws SQLException;
}
