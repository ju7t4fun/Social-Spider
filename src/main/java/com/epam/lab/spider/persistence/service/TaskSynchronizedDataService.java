package com.epam.lab.spider.persistence.service;


import com.epam.lab.spider.model.entity.sync.TaskSynchronized;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.TaskSynchronizedDataDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Yura Kovalik
 */
public class TaskSynchronizedDataService {
    private static final Logger LOG = Logger.getLogger(TaskSynchronizedDataService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private TaskSynchronizedDataDAO tsdDao = factory.create(TaskSynchronizedDataDAO.class);

    public Integer getIdLastProcessedPost(Task task, Wall wall) {

        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getIdLastProcessedPost(connection, task.getId(), wall.getId());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }

    }

    public Set<Integer> getProcessedPost(Task task, Wall wall, int limit) {

        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.getProcessedPost(connection, task.getId(), wall.getId(), limit);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return new HashSet<>();
        }

    }

    public Boolean markIdLastProcessedPost(Task task, Wall wall, Integer vkInnerPostId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.markIdLastProcessedPost(connection, vkInnerPostId, task.getId(), wall.getId());
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
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
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
        return result;
    }

    public Map<Long, Integer> statisticsExecution(String date) {
        try (Connection connection = PoolConnection.getConnection()) {
            return tsdDao.statisticsExecution(connection, date);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
