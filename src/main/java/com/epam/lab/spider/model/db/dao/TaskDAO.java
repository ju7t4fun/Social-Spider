package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public interface TaskDAO extends CRUD<Task>,SavableDAO<Task> {

    List<Task> getByUserId(Connection connection, int id) throws SQLException;

    List<Task> getByCategoryId(Connection connection, int id) throws SQLException;

    public Task getByIdLimitByUserId(Connection connection, int id, int userId) throws SQLException;

    public List<Task> getAllByNextRunDateLimitByState(Connection connection, Date date, Task.State state) throws SQLException;

    public boolean updateNextTimeRun(Connection connection, int id, Date date) throws SQLException;

    public boolean updateState(Connection connection, int id, Task.State state) throws SQLException;

    public boolean updateNextTimeRunAndState(Connection connection, int id, Date date,Task.State state) throws SQLException;

    int getCount(Connection connection) throws SQLException;

}
