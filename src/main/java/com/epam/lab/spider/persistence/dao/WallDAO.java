package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dzyuba Orest
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

    List<Wall> getByUserId(Connection connection, int userId) throws SQLException;

    List<Wall> getReadByUserId(Connection connection, int userId) throws SQLException;

    List<Wall> getWriteByUserId(Connection connection, int userId) throws SQLException;

    List<Wall> getByOwnerId(Connection connection, int id) throws SQLException;

    List<Wall> getReadByOwnerId(Connection connection, int ownerId) throws SQLException;

    List<Wall> getWriteByOwnerId(Connection connection, int ownerId) throws SQLException;

    List<Wall> getWriteByAdmin(Connection connection) throws SQLException;

    boolean deleteByOwnerId(Connection connection, int ownerDd) throws SQLException;
}
