package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Profile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public interface ProfileDAO extends CRUD<Profile> {

    List<Profile> getByUserId(Connection connection, int id) throws SQLException;

    Profile getByVkId(Connection connection, int vkId) throws SQLException;

    List<Profile> getNotInWall(Connection connection, int owner_id) throws SQLException;

    List<Profile> getInWall(Connection connection, int owner_id) throws SQLException;

    List<Profile> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getCountByUserId(Connection connection, Integer id) throws SQLException;
}
