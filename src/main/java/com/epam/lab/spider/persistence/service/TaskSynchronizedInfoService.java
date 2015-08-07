package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.SynchronizedData;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.TaskSynchronizedInfoDAO;
import com.epam.lab.spider.persistence.factory.SynchronizedDataAbstractFactory;
import com.epam.lab.spider.persistence.factory.SynchronizedDataAuditableFactoryImpl;
import com.epam.lab.spider.persistence.factory.SynchronizedDataFactoryImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public class TaskSynchronizedInfoService {
    private static final Logger LOG = Logger.getLogger(TaskSynchronizedInfoService.class);

    private static final boolean AUDITABLE = true;
    private static final SynchronizedDataAbstractFactory synchronizedDataFactory;
    private static TaskSynchronizedInfoDAO taskSynchronizedInfoDAO;
    private static boolean created = false;

    static {
        if (AUDITABLE) {
            synchronizedDataFactory = new SynchronizedDataAuditableFactoryImpl();
        } else {
            synchronizedDataFactory = new SynchronizedDataFactoryImpl();
        }
        taskSynchronizedInfoDAO = synchronizedDataFactory.createTaskSynchronizedNewDataDAO();
    }

    public static SynchronizedDataAbstractFactory getSynchronizedDataFactory() {
        return synchronizedDataFactory;
    }
    public void createTableIfNotExist(){
        if(!created) {
            try (Connection connection = PoolConnection.getConnection()) {
                taskSynchronizedInfoDAO.createTable(connection);
                created = true;
            } catch (SQLException e) {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
    }
    protected boolean insert(Task task, Wall wall, Integer offset, Integer lastId ){
        return insert(task.getId(),wall.getId(),offset,lastId);
    }
    protected boolean insert(Integer taskId, Integer wallId, Integer offset, Integer lastId ){
            try (Connection connection = PoolConnection.getConnection()) {
                createTableIfNotExist();
                return taskSynchronizedInfoDAO.insert(connection,synchronizedDataFactory.createSynchronizedData(taskId,wallId,offset,lastId));
            } catch (SQLException e) {
                LOG.error(e.getLocalizedMessage(), e);
                return false;
            }
    }
    protected boolean update(SynchronizedData sync){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedInfoDAO.update(connection,sync);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    public boolean delete(Task task, Wall wall){
        return delete(task.getId(), wall.getId());
    }
    public boolean delete(Integer taskId, Integer wallId){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedInfoDAO.delete(connection, taskId, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }
    public SynchronizedData getBy(Task task, Wall wall){
        return getBy(task.getId(),wall.getId());
    }
    public SynchronizedData getBy(Integer taskId, Integer wallId){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            return taskSynchronizedInfoDAO.getById(connection, taskId, wallId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return null;
        }
    }
    public boolean save(SynchronizedData syncNew){
        try (Connection connection = PoolConnection.getConnection()) {
            createTableIfNotExist();
            SynchronizedData sync = taskSynchronizedInfoDAO.getById(connection, syncNew.getTaskId(), syncNew.getWallId());
            if(sync!=null){
                sync.syncWith(syncNew);
                return taskSynchronizedInfoDAO.update(connection,sync);
            }
            else return taskSynchronizedInfoDAO.insert(connection,syncNew);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
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
