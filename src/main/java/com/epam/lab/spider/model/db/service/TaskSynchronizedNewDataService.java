package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.TaskSynchronizedNewDataDAO;
import com.epam.lab.spider.model.db.dao.mysql.TaskSynchronizedNewDataDAOImpl;
import com.epam.lab.spider.model.db.entity.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hell-engine on 7/10/2015.
 */
public class TaskSynchronizedNewDataService {
    public static TaskSynchronizedNewDataDAO taskSynchronizedNewDataDAO = new TaskSynchronizedNewDataDAOImpl();
    public static boolean created = false;

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
    public boolean insert(Integer taskId, Integer wallId, Integer offset, Integer lastId ){
            try (Connection connection = PoolConnection.getConnection()) {
                createTableIfNotExist();
                return taskSynchronizedNewDataDAO.insert(connection,taskId,wallId,offset,lastId);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
    public boolean insert(Task task, Wall wall, Integer offset, Integer lastId ){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.insert(connection,task.getId(),wall.getId(),offset,lastId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(SynchronizedData sync){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.insert(connection,sync.getTaskId(),sync.getWallId(),sync.getPostOffset(),sync.getPostVkId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Integer taskId, Integer wallId){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.delete(connection, taskId, wallId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Task task, Wall wall){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.delete(connection, task.getId(), wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public SynchronizedData getBy(Task task, Wall wall){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedNewDataDAO.getById(connection, task.getId(), wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public boolean save(Integer taskId, Integer wallId, Integer offset, Integer lastId ){
        this.delete(taskId,wallId);
        return this.insert(taskId,wallId,offset,lastId);
    }
    public boolean save(Task task, Wall wall, Integer offset, Integer lastId ){
        return this.save(task.getId(), wall.getId(), offset, lastId);
    }
    public boolean save(SynchronizedData sync){
        return this.save(sync.getTaskId(),sync.getWallId(),sync.getPostOffset(),sync.getPostVkId());
    }
}
