package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.persistence.dao.savable.SavableDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author Marian Voronovskyi
 */
public interface TaskDAO extends CRUD<Task>,SavableDAO<Task> {

    List<Task> getByUserId(Connection connection, int id) throws SQLException;

    List<Task> getByCategoryId(Connection connection, int id) throws SQLException;

    Task getByIdLimitByUserId(Connection connection, int id, int userId) throws SQLException;

     List<Task> getAllByNextRunDateLimitByState(Connection connection, Date date, Task.State state) throws SQLException;

   boolean updateNextTimeRun(Connection connection, int id, Date date) throws SQLException;

   boolean updateState(Connection connection, int id, Task.State state) throws SQLException;

    boolean updateNextTimeRunAndState(Connection connection, int id, Date date,Task.State state) throws SQLException;

    int getCount(Connection connection, int userId) throws SQLException;

    List<Task> getAllLimited(Connection connection, int userId, int start, int amount) throws SQLException;

    List<Task> getAllActiveLimited(Connection connection, int userId, int start, int amount) throws SQLException;

    int getActiveCount(Connection connection, int userId) throws SQLException;

    Task getByIdAdminRules(Connection connection, int id) throws SQLException;

    List<Task> getAllLimitedAdmin(Connection connection, int start, int amount) throws SQLException;

    int getCountAdmin(Connection connection) throws SQLException;


}
