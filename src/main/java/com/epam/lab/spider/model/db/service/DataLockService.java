package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.DataLockDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.DataLock;
import com.epam.lab.spider.model.db.entity.Event;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by shell on 6/21/2015.
 */
public class DataLockService {
    public static final Logger LOG = Logger.getLogger(DataLockService.class);
    private static DAOFactory factory = DAOFactory.getInstance();
    private static DataLockDAO dataLockDAO = factory.create(DataLockDAO.class);



    public List<DataLock> dataLockByUser(Integer userId){
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.dataLockByUserId(connection,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public Map<String, Map<Integer, Set<DataLock.Mode>>> restore() {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.restore(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException eee){
            LOG.fatal("NullPointerException",eee);
            eee.printStackTrace();
        }
        return new HashMap<>();
    }

    public boolean createLock(String table, Integer index, DataLock.Mode mode, Integer userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.createLock(connection,table,index,mode,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteLock(String table, Integer index, DataLock.Mode mode) {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.deleteLock(connection,table,index,mode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
