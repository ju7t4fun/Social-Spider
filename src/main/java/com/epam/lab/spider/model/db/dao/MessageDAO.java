package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 23.06.2015.
 */
public interface MessageDAO extends CRUD<Message> {

    List<Message> getByUserId(Connection connection, int userId) throws SQLException;

    List<Message> getByUserId(Connection connection, int userId, int offset, int limit) throws SQLException;

    int getCountUnReadByUserId(Connection connection, int userId) throws SQLException;

    boolean markAsReadByUserId(Connection connection, int userId) throws SQLException;

    Map<Integer, Integer> getListUnReadAdminMessages(Connection connection, int limit) throws SQLException;

    int getCountUnReadByAdminId(Connection connection) throws SQLException;

    List<Message> getLastUnReadMessagesByAdmin(Connection connection, int limit) throws SQLException;

}
