package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Marian Voronovskyi
 */
public interface OwnerDAO extends CRUD<Owner> {

    List<Owner> getLimited(Connection connection, Integer begin, Integer end) throws SQLException;

    List<Owner> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    Owner getByVkId(Connection connection, int vk_id) throws SQLException;

    List<Owner> getByUserId(Connection connection, int userId) throws SQLException;

    List<Owner> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getCountByUserId(Connection connection, Integer id) throws SQLException;

    List<Owner> searchByUserId(Connection connection, Integer id, String q, int page, int size) throws SQLException;

    int getCountSearchByUserId(Connection connection, Integer id, String q) throws SQLException;

    List<Owner> getAllGroups(Connection connection, int start, int ammount) throws SQLException;

    boolean updateBan(Connection connection, int vk_id) throws SQLException;

    boolean updateUnBan(Connection connection, int vk_id) throws SQLException;

    int getCountAllUnique(Connection connection) throws SQLException;

    List<Owner> getAllGroupsWithSearch(Connection connection, String nameToSearch, int start, int ammount) throws
            SQLException;

    int getCountAllUniqueWithSearch(Connection connection, String nameToSearch) throws SQLException;

    List<Owner> getAllByVkId(Connection connection, int vkId) throws SQLException;

    boolean isBannedByVkId(Connection connection, int id) throws SQLException;

    boolean hasUserGroup(Connection connection, int id, int userId) throws SQLException;
}
