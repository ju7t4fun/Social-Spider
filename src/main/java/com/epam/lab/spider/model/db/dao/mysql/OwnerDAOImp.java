package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.OwnerDAO;
import com.epam.lab.spider.model.db.entity.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class OwnerDAOImp extends BaseDAO implements OwnerDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO owner (vk_id, name, domain, deleted, user_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE owner SET vk_id = ?, name = ?, domain = ?, deleted = ?, " +
            "user_id = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE * FROM owner WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE owner SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM owner WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM owner WHERE id = ? AND deleted = false";
    private static final String SQL_GET_LIMITED_QUERY = "SELECT * FROM owner WHERE deleted = false LIMIT ?, ?";
    private static final String SQL_GET_BY_VK_ID_QUERY = "SELECT * FROM owner WHERE vk_id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM owner WHERE user_id = ? AND deleted = 0";
    private static final String SQL_GET_BY_USER_ID_LIMIT_QUERY = "SELECT * FROM owner WHERE user_id = ? AND deleted =" +
            " 0 ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_GET_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM owner WHERE user_id=? AND deleted = 0";
    private static final String SQL_SEARCH_BY_USER_ID_QUERY = "SELECT * FROM owner WHERE user_id = ? AND MATCH (name," +
            " domain) AGAINST (?) ORDER BY id DESC LIMIT ?,?";
    private static final String SQL_COUNT_SEARCH_BY_USER_ID_QUERY = "SELECT * FROM owner WHERE user_id = ? AND MATCH " +
            "(name, domain) AGAINST (?)";

    private static final String SQL_GET_ALL_UNIQUE_VKID = "SELECT  * FROM owner Group BY vk_id ORDER BY id LIMIT ?, ?";
    private static final String SQL_GET_ALL_UNIQUE_WITH_SEARCH__VKID = "SELECT * FROM owner WHERE name LIKE ? Group BY vk_id ORDER BY id LIMIT ?, ?";
    private static final String SQL_UPDATE_BAN = "UPDATE  owner SET  banned=1 WHERE vk_id=?";
    private static final String SQL_UPDATE_UNBAN = "UPDATE  owner SET  banned=0 WHERE vk_id=?";
    private static final String SQL_GET_COUNT_ALL_UNIQUE_VKID = "SELECT  COUNT(*) FROM (  SELECT * FROM owner Group BY vk_id) AS t";
    private static final String SQL_GET_COUNT_ALL_UNIQUE_WITH_SEARCH__VKID = "SELECT  COUNT(*) FROM ( SELECT * FROM owner WHERE name LIKE ? Group BY vk_id ) AS t";

    @Override
    public boolean insert(Connection connection, Owner owner) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                owner.getVkId(),
                owner.getName(),
                owner.getDomain(),
                owner.getDeleted(),
                owner.getUserId());
        owner.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Owner owner) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                owner.getVkId(),
                owner.getName(),
                owner.getDomain(),
                owner.getDeleted(),
                owner.getUserId(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Owner> select(Connection connection, String query, Object... args) throws SQLException {
        List<Owner> ownerList = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Owner owner;
        while (rs.next()) {
            owner = new Owner();
            owner.setId(rs.getInt("id"));
            owner.setVk_id(rs.getInt("vk_id"));
            owner.setName(rs.getString("name"));
            owner.setDomain((rs.getString("domain")));
            owner.setDeleted(rs.getBoolean("deleted"));
            owner.setBanned(rs.getBoolean("banned"));
            ownerList.add(owner);
        }
        return ownerList;
    }

    @Override
    public List<Owner> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Owner getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Owner> getLimited(Connection connection, Integer begin, Integer end) throws SQLException {
        return select(connection, SQL_GET_LIMITED_QUERY, begin, end);
    }

    @Override
    public Owner getByVkId(Connection connection, int vk_id) throws SQLException {
        return first(select(connection, SQL_GET_BY_VK_ID_QUERY, vk_id));
    }

    @Override
    public List<Owner> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

    @Override
    public List<Owner> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_LIMIT_QUERY, id, page, size);
    }

    @Override
    public int getCountByUserId(Connection connection, Integer id) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_BY_USER_ID, id);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }

    @Override
    public List<Owner> searchByUserId(Connection connection, Integer id, String q, int page, int size) throws
            SQLException {
        return select(connection, SQL_SEARCH_BY_USER_ID_QUERY, id, q, page, size);
    }

    @Override
    public int getCountSearchByUserId(Connection connection, Integer id, String q) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_COUNT_SEARCH_BY_USER_ID_QUERY, id, q);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }

    //should remake later
    @Override
    public List<Owner> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException {
        List<Owner> ownerList = new ArrayList<>();
        ResultSet rs = selectQuery(connection, SQL_SOME_QUERY);
        Owner owner;
        while (rs.next()) {
            owner = new Owner();
            owner.setVk_id(rs.getInt("vk_id"));
            owner.setName(rs.getString("name"));
            ownerList.add(owner);
        }
        return ownerList;
    }

    //should remake later
    @Override
    public int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException {

        ResultSet rs = selectQuery(connection, SQL_SOME_QUERY);
        if (rs != null) {
            while (rs.next()) {
                System.out.println(rs.getString(1));
                return Integer.parseInt(rs.getString(1));
            }
        }
        return -322;
    }

    @Override
    public List<Owner> getAllGroups(Connection connection, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_UNIQUE_VKID,start,ammount);
    }

    @Override
    public List<Owner> getAllGroupsWithSearch(Connection connection, String nameToSearch, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_UNIQUE_WITH_SEARCH__VKID,nameToSearch,start,ammount);
    }

    public boolean updateBan(Connection connection, int vk_id) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_BAN, vk_id);
    }

    public boolean updateUnBan(Connection connection, int vk_id) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_UNBAN, vk_id);
    }
    @Override
    public int getCountAllUnique(Connection connection) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_ALL_UNIQUE_VKID);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }
    @Override
    public int getCountAllUniqueWithSearch(Connection connection, String nameToSearch) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_ALL_UNIQUE_WITH_SEARCH__VKID, nameToSearch);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }
}
