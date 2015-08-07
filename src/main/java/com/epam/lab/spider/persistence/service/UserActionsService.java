package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.UserActions;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.UserActionsDAO;
import com.epam.lab.spider.persistence.dao.mysql.UserActionsDAOImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public class UserActionsService {
    private static final Logger LOG = Logger.getLogger(UserActionsService.class);

    private static final UserActionsDAO<UserActions> userActionsDao = new UserActionsDAOImpl();

    private static boolean created = false;


    protected  void createTableIfNotExist(Connection connection) throws SQLException {
        if(!created) {
            userActionsDao.createTable(connection);
            created = true;
        }
    }

    public void createTableIfNotExist(){
        if(!created) {
            try (Connection connection = PoolConnection.getConnection()) {
                userActionsDao.createTable(connection);
                created = true;
            } catch (SQLException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public UserActions getById(Integer id){
        UserActions userActions = null;
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            userActions = userActionsDao.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return userActions;
    }
    public UserActions getByUserId(Integer userId){
        UserActions userActions = null;
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            userActions = userActionsDao.getByUserId(connection, userId, new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        if(userActions == null){
            userActions = BasicEntityFactory.getSynchronized().createUserActions();
            userActions.setUserId(userId);
        }
        return userActions;
    }
    public boolean save(UserActions actions){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist(connection);
            if(actions!=null){
                if(actions.getId() == null)return userActionsDao.insert(connection,actions,new Date(System.currentTimeMillis()));
                else return userActionsDao.update(connection,actions);
            }
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);

        }
        return false;
    }
}
