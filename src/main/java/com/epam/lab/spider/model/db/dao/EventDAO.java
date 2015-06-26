package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public interface EventDAO extends CRUD<Event> {

    List<Event> getByUserId(Connection connection, int userId) throws SQLException;

    List<Event> getByShownUserId(Connection connection, int clientId) throws SQLException;

    boolean markAsShowByUserId(Connection connection, int clientId) throws SQLException;

    List<Event> getLastUnShownByUserId(Connection connection, int id, int limit) throws SQLException;

}
