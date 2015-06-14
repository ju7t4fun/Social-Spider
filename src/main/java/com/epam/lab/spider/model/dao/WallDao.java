package com.epam.lab.spider.model.dao;

import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dmytro on 12.06.2015.
 */
public interface WallDao extends CRUD<Wall> {

    List<Wall> getDestinationByTaskId(Connection connection, int id) throws SQLException;

    List<Wall> getSourceByTaskId(Connection connection, int id) throws SQLException;

    boolean deleteByProfileId(Connection connection, int id) throws SQLException;

}
