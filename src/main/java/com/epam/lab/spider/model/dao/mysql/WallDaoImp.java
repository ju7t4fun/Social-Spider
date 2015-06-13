package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.WallDao;
import com.epam.lab.spider.model.entity.Wall;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class WallDaoImp extends BaseDAO implements WallDao {


    private static final String SQL_INSERT_QUERY = "INSERT INTO wall (id, owner_id, profile_id, permission" +
            " VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE wall SET owner_id= ?, profile_id = ?, permission = ?" +
            " WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE FROM wall WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE wall SET deleted = true WHERE id = ?";

    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM wall";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM wall WHERE id = ?";

    @Override
    public boolean insert(Connection connection, Wall wall) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, wall.getId(), wall.getOwner_id(), wall.getProfile_id(),
                wall.getPermission());
    }

    @Override
    public boolean update(Connection connection, int id, Wall wall) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, wall.getOwner_id(), wall.getProfile_id(), wall.getPermission(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Wall> select(Connection connection, String query, Object... args) throws SQLException {
        List<Wall> walls = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Wall  wall;
        while (rs.next()) {
            wall = new Wall();
            wall.setId(rs.getInt("id"));
            wall.setOwner_id(rs.getInt("owner_id"));
            wall.setProfile_id(rs.getInt("profile_id"));
            wall.setPermission(Wall.Permission.valueOf(rs.getString("permission").toUpperCase()));
            walls.add(wall);
        }
        return walls;
    }

    @Override
    public List<Wall> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Wall getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

}
