package com.epam.lab.spider.model.dao;

import com.epam.lab.spider.model.entity.Profile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public interface ProfileDAO extends CRUD<Profile> {

    List<Profile> getByUserId(Connection connection, int id) throws SQLException;

}
