package com.epam.lab.spider.model.dao;

import com.epam.lab.spider.model.entity.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public interface TaskDAO extends CRUD<Task> {

    List<Task> getByUserId(Connection connection, int id) throws SQLException;

    List<Task> getByCategoryId(Connection connection, int id) throws SQLException;

}
