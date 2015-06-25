package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.DataLock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by shell on 6/20/2015.
 */
public interface DataLockDAO {
    public DataLock dataLockByEventId(Connection conn, Integer eventID) throws SQLException;
    public Map<String, Map<Integer, Set<DataLock.Mode>>> restore(Connection conn) throws SQLException ;
    public boolean createLock(Connection conn, String table, Integer index, DataLock.Mode mode, Integer eventId) throws SQLException ;
    public boolean deleteLock(Connection conn, String table, Integer index, DataLock.Mode mode) throws SQLException;
}
