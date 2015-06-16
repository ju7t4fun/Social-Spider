package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.TaskSynchronizedDataDAO;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shell on 6/16/2015.
 */
public class TaskSynchronizedDataService {
    private DAOFactory factory = DAOFactory.getInstance();
    TaskSynchronizedDataDAO tsdDao = factory.create(TaskSynchronizedDataDAO.class);
    public Integer getIdLastProcessedPost(Task task, Wall wall){

        try(Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getIdLastProcessedPost(connection,task.getId(),wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Set<Integer> getProcessedPost(Task task, Wall wall, int limit){

        try(Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getProcessedPost(connection,task.getId(),wall.getId(),limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashSet<>();
        }

    }
    public Boolean markIdLastProcessedPost(Task task, Wall wall, Integer vkInnerPostId){
        try(Connection connection = PoolConnection.getConnection()) {
            return tsdDao.markIdLastProcessedPost(connection, vkInnerPostId,task.getId(), wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Boolean markIdLastProcessedPost(Task task, Wall wall, List<Integer> vkInnerPostIdList){
        Boolean result = true;
        try(Connection connection = PoolConnection.getConnection()) {
            for(Integer vkInnerPostId:vkInnerPostIdList) {
                result &= tsdDao.markIdLastProcessedPost(connection, vkInnerPostId, task.getId(), wall.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return  result;
    }
}
