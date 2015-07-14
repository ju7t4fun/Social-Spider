package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.dao.mysql.TaskSynchronizedNewDataAuditableDAOImpl;
import com.epam.lab.spider.model.db.entity.*;
import com.epam.lab.spider.model.db.factory.SynchronizedDataAbstractFactory;
import com.epam.lab.spider.model.db.factory.SynchronizedDataAuditableFactoryImpl;
import com.epam.lab.spider.model.db.factory.SynchronizedDataFactoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class TaskSynchronizedNewDataService {
    private static final boolean AUDITABLE = true;
    private static TaskSynchronizedNewDataDAO taskSynchronizedNewDataDAO;


    private static final SynchronizedDataAbstractFactory synchronizedDataFactory;

    static {
        if (AUDITABLE) {
            synchronizedDataFactory = new SynchronizedDataAuditableFactoryImpl();
        } else {
            synchronizedDataFactory = new SynchronizedDataFactoryImpl();
        }
        taskSynchronizedNewDataDAO = synchronizedDataFactory.createTaskSynchronizedNewDataDAO();
    }

    private static boolean created = false;

    public static SynchronizedDataAbstractFactory getSynchronizedDataFactory() {
        return synchronizedDataFactory;
    }
    public void createTableIfNotExist(){
        if(!created) {
            try (Connection connection = PoolConnection.getConnection()) {
                taskSynchronizedNewDataDAO.createTable(connection);
                created = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected boolean insert(Task task, Wall wall, Integer offset, Integer lastId ){
        return insert(task.getId(),wall.getId(),offset,lastId);
    }
    protected boolean insert(Integer taskId, Integer wallId, Integer offset, Integer lastId ){
            try (Connection connection = PoolConnection.getConnection()) {
                createTableIfNotExist();
                return taskSynchronizedNewDataDAO.insert(connection,synchronizedDataFactory.createSynchronizedData(taskId,wallId,offset,lastId));
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
    protected boolean update(SynchronizedData sync){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.update(connection,sync);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    public boolean delete(Task task, Wall wall){
//        return delete(task.getId(), wall.getId());
//    }
//    public boolean delete(Integer taskId, Integer wallId){
//        try (Connection connection = PoolConnection.getConnection()) {
//            createTableIfNotExist();
//            return taskSynchronizedNewDataDAO.delete(connection, taskId, wallId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    public SynchronizedData getBy(Task task, Wall wall){
        return getBy(task.getId(),wall.getId());
    }
    public SynchronizedData getBy(Integer taskId, Integer wallId){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.getById(connection, taskId, wallId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean save(SynchronizedData syncNew){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            SynchronizedData sync = taskSynchronizedNewDataDAO.getById(connection, syncNew.getTaskId(), syncNew.getWallId());
            if(sync!=null){
                sync.syncWith(syncNew);
                return taskSynchronizedNewDataDAO.update(connection,sync);
            }
            else return taskSynchronizedNewDataDAO.insert(connection,syncNew);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean save(Task task, Wall wall, Integer offset, Integer lastId ){
        return this.save(task.getId(), wall.getId(), offset, lastId);
    }
    public boolean save(Integer taskId, Integer wallId, Integer offset, Integer lastId ){
        return save(synchronizedDataFactory.createSynchronizedData(taskId,wallId,offset,lastId));
    }
}
