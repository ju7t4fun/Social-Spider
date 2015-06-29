package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public interface OwnerDAO extends CRUD<Owner> {

    List<Owner> getLimited(Connection connection, Integer begin, Integer end) throws SQLException;

    List<Owner> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    Owner getByVkId(Connection connection, int vk_id) throws SQLException;

    List<Owner> getByUserId(Connection connection, int userId) throws SQLException;

    List<Owner> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getCountByUserId(Connection connection, Integer id) throws SQLException;
}
