package com.epam.lab.spider.model.db.service;


import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.TaskSynchronizedDataDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Task;
import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by shell on 6/16/2015.
 */
public class TaskSynchronizedDataService {
    private DAOFactory factory = DAOFactory.getInstance();
    TaskSynchronizedDataDAO tsdDao = factory.create(TaskSynchronizedDataDAO.class);

    public Integer getIdLastProcessedPost(Task task, Wall wall) {

        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getIdLastProcessedPost(connection, task.getId(), wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Set<Integer> getProcessedPost(Task task, Wall wall, int limit) {

        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getProcessedPost(connection, task.getId(), wall.getId(), limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashSet<>();
        }

    }

    public Boolean markIdLastProcessedPost(Task task, Wall wall, Integer vkInnerPostId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.markIdLastProcessedPost(connection, vkInnerPostId, task.getId(), wall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean markIdLastProcessedPost(Task task, Wall wall, List<Integer> vkInnerPostIdList) {
        Boolean result = true;
        try (Connection connection = PoolConnection.getConnection()) {
            for (Integer vkInnerPostId : vkInnerPostIdList) {
                result &= tsdDao.markIdLastProcessedPost(connection, vkInnerPostId, task.getId(), wall.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public Map<Long, Integer> statisticsExecution(String date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.statisticsExecution(connection, date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
