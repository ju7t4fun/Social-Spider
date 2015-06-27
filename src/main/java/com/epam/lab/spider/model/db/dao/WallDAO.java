package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dmytro on 12.06.2015.
 */
public interface WallDAO extends CRUD<Wall> {

    List<Wall> getDestinationByTaskId(Connection connection, int id) throws SQLException;

    List<Wall> getSourceByTaskId(Connection connection, int id) throws SQLException;

    boolean deleteByProfileId(Connection connection, int id) throws SQLException;

    List<Wall> getByProfileId(Connection connection, int id) throws SQLException;

    List<Wall> getAllByProfileID(Connection connection, int profile_id) throws SQLException;

    boolean checkedExist(Connection connection, Wall wall) throws SQLException;

    List<Wall> getAllByOwnerIdAndPermission(Connection connection, int owner_id, Wall.Permission permission) throws
            SQLException;

    boolean deleteByOwnerId(Connection connection, int owner_id, int profile_id, Wall.Permission permission) throws
            SQLException;

    boolean updateOnActive(Connection connection, int owner_id, int profile_id, Wall.Permission permission) throws
            SQLException;

    List<Wall> getByProfileId(Connection connection, int id) throws SQLException;

    List<Wall> getByUserId(Connection connection, int id) throws SQLException;
}
