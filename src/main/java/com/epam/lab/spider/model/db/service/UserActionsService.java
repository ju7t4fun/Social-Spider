package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.dao.UserActionsDAO;
import com.epam.lab.spider.model.db.dao.mysql.UserActionsDAOImpl;
import com.epam.lab.spider.model.db.entity.SynchronizedData;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.UserActions;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.factory.SynchronizedDataAbstractFactory;
import com.epam.lab.spider.model.db.factory.SynchronizedDataAuditableFactoryImpl;
import com.epam.lab.spider.model.db.factory.SynchronizedDataFactoryImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class UserActionsService {

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
                e.printStackTrace();
            }
        }
    }

    public UserActions getById(Integer id){
        UserActions userActions = null;
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            userActions = userActionsDao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActions;
    }
    public UserActions getByUserId(Integer userId){
        UserActions userActions = null;
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            userActions = userActionsDao.getByUserId(connection, userId, new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userActions == null){
            userActions = new UserActions();
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
            e.printStackTrace();

        }
        return false;
    }
}
