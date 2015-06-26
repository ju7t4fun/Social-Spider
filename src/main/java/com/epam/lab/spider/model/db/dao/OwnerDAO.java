package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.entity.Owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public interface OwnerDAO extends CRUD<Owner> {
    List<Owner> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;
    Owner getByVkId(Connection connection, int vk_id) throws SQLException;
}
