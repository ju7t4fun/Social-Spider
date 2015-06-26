package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Profile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public interface ProfileDAO extends CRUD<Profile> {

    List<Profile> getByUserId(Connection connection, int id) throws SQLException;

    Profile getByVkId(Connection connection, int vkId) throws SQLException;

    List<Profile> getNotInWall(Connection connection, int owner_id) throws SQLException;

    List<Profile> getInWall(Connection connection, int owner_id) throws SQLException;

}
