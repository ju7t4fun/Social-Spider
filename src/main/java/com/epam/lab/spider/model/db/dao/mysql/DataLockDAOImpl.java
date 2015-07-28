package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.DataLockDAO;
import com.epam.lab.spider.model.db.entity.DataLock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by shell on 6/20/2015.
 */
public class DataLockDAOImpl extends BaseDAO implements DataLockDAO {

    private static final String SQL_RESTORE_LOCK_QUERY = "SELECT key_index,key_table,value FROM data_lock";
    private static final String SQL_CREATE_LOCK_QUERY = "INSERT INTO data_lock (key_table, key_index, value, user_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_LOCK_QUERY = "DELETE FROM data_lock WHERE key_table=? AND key_index = ? AND value = ?";
    private static final String SQL_SELECT_DATA_LOCK_QUERY_BY_EVENT_ID = "SELECT key_index,key_table,value FROM data_lock WHERE user_id = ?";



    public List<DataLock> dataLockByUserId(Connection conn, Integer eventID) throws SQLException {
        List<DataLock> dataLocks = new ArrayList<>();
        ResultSet rs = selectQuery(conn, SQL_SELECT_DATA_LOCK_QUERY_BY_EVENT_ID, eventID);
        int colTable, colIndex, colMode;
        String table;
        Integer index;
        DataLock.Mode mode;
        colTable = rs.findColumn("key_table");
        colIndex = rs.findColumn("key_index");
        colMode = rs.findColumn("value");
        while (rs.next()){
            DataLock dataLock;
            table = rs.getString(colTable);
            index = rs.getInt(colIndex);
            mode =  DataLock.Mode.valueOf(rs.getString(colMode));
            dataLock = new DataLock(table,index,mode);
            dataLocks.add(dataLock);
        }
        return dataLocks;
    }

    public Map<String, Map<Integer, Set<DataLock.Mode>>> restore(Connection conn) throws SQLException {
        Map<String, Map<Integer, Set<DataLock.Mode>>> globalMap = new HashMap<>();
        ResultSet rs = selectQuery(conn, SQL_RESTORE_LOCK_QUERY);
        int colTable, colIndex, colMode;
        String table;
        Integer index;
        DataLock.Mode mode;
        colTable = rs.findColumn("key_table");
        colIndex = rs.findColumn("key_index");
        colMode = rs.findColumn("value");
        while (rs.next()){
            table = rs.getString(colTable);
            index = rs.getInt(colIndex);
            mode =  DataLock.Mode.valueOf(rs.getString(colMode));
            Map<Integer, Set<DataLock.Mode>> localMap = globalMap.get(table);
            if(localMap == null){
                localMap = new HashMap<>();
                globalMap.put(table,localMap);
            }
            Set<DataLock.Mode> localSet = localMap.get(index);
            if(localSet == null){
                localSet = new HashSet<>();
                localMap.put(index,localSet);
            }
            localSet.add(mode);
        }
        return globalMap;
    }
    public boolean createLock(Connection conn, String table, Integer index, DataLock.Mode mode, Integer userId)
            throws SQLException {
        return this.changeQuery(conn,SQL_CREATE_LOCK_QUERY,table,index,mode, userId);
    }
    public boolean deleteLock(Connection conn, String table, Integer index, DataLock.Mode mode) throws SQLException {
        return this.changeQuery(conn,SQL_DELETE_LOCK_QUERY,table,index,mode);
    }


}
