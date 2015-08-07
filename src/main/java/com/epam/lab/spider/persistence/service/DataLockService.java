package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.DataLock;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.DataLockDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Yura Kovalik
 */
public class DataLockService {
    public static final Logger LOG = Logger.getLogger(DataLockService.class);
    private static DAOFactory factory = DAOFactory.getInstance();
    private static DataLockDAO dataLockDAO = factory.create(DataLockDAO.class);



    public List<DataLock> dataLockByUser(Integer userId){
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.dataLockByUserId(connection,userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return new ArrayList<>();
    }
    public Map<String, Map<Integer, Set<DataLock.Mode>>> restore() {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.restore(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        } catch (NullPointerException eee){
            LOG.fatal("NullPointerException",eee);
            LOG.error(eee.getLocalizedMessage(), eee);
        }
        return new HashMap<>();
    }

    public boolean createLock(String table, Integer index, DataLock.Mode mode, Integer userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.createLock(connection,table,index,mode,userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    public boolean deleteLock(String table, Integer index, DataLock.Mode mode) {
        try (Connection connection = PoolConnection.getConnection()) {
            return dataLockDAO.deleteLock(connection,table,index,mode);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
