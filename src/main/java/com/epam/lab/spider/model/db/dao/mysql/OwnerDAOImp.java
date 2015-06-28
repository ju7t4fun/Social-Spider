package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.OwnerDAO;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;

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
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE owner SET vk_id = ?, name = ?, domain = ?, deleted = ? " +
            "user_id = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE * FROM owner WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE owner SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM owner WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM owner WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_VK_ID_QUERY = "SELECT * FROM owner WHERE vk_id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM owner WHERE user_id = ? AND deleted = 0";

    @Override
    public boolean insert(Connection connection, Owner owner) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                owner.getVk_id(),
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
                owner.getVk_id(),
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
    public Owner getByVkId(Connection connection, int vk_id) throws SQLException {
        return first(select(connection,  SQL_GET_BY_VK_ID_QUERY, vk_id));
    }

    @Override
    public List<Owner> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

    //should remake later
    @Override
    public List<Owner> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException {
        List<Owner> ownerList= new ArrayList<>();
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
        if (rs!=null) {
            while(rs.next()) {
                System.out.println(rs.getString(1));
                return Integer.parseInt(rs.getString(1));
            }
        }
        return -322;
    }




}
