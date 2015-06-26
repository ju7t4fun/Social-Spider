package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.WallDAO;
import com.epam.lab.spider.model.db.entity.Wall;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 12.06.2015.
 */
public class WallDAOImp extends BaseDAO implements WallDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO wall (id, owner_id, profile_id, permission, deleted" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE wall SET owner_id = ?, profile_id = ?, permission = ?, " +
            "deleted = ? WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE FROM wall WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE wall SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM wall WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM wall WHERE id = ? AND deleted = false";
    private static final String SQL_GET_DESTINATION_BY_TASK_ID_QUERY = "SELECT * FROM wall JOIN task_destination ON " +
            "task_destination.wall_id = wall.id WHERE task_id = ?";
    private static final String SQL_GET_SOURCE_BY_TASK_ID_QUERY = "SELECT * FROM wall JOIN task_source ON " +
            "task_source.wall_id = wall.id WHERE task_id = ?";
    private static final String SQL_DELETED_BY_PROFILE_ID_QUERY = "UPDATE wall SET delete = true WHERE profile_id = ?" +
            " deleted = false";
    private static final String SQL_GET_ALL_BY_PROFILE_ID_QUERY = "SELECT * FROM wall WHERE profile_id = ? AND deleted = false";

    @Override
    public boolean insert(Connection connection, Wall wall) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                wall.getId(),
                wall.getOwner_id(),
                wall.getProfile_id(),
                wall.getPermission(),
                wall.getDeleted());
        wall.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Wall wall) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                wall.getOwner_id(),
                wall.getProfile_id(),
                wall.getPermission(),
                wall.getDeleted(),
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
        Wall wall;
        while (rs.next()) {
            wall = new Wall();
            wall.setId(rs.getInt("id"));
            wall.setOwner_id(rs.getInt("owner_id"));
            wall.setProfile_id(rs.getInt("profile_id"));
            wall.setPermission(Wall.Permission.valueOf(rs.getString("permission").toUpperCase()));
            wall.setDeleted(rs.getBoolean("deleted"));
            walls.add(wall);
        }
        return walls;
    }

    @Override
    public List<Wall> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public List<Wall> getAllByProfileID(Connection connection, int profile_id) throws SQLException {
        List<Wall> walls = new ArrayList<>();
        ResultSet rs = selectQuery(connection, SQL_GET_ALL_BY_PROFILE_ID_QUERY, profile_id);
        Wall wall;
        while (rs.next()) {
            wall = new Wall();
            wall.setId(rs.getInt("id"));
            wall.setOwner_id(rs.getInt("owner_id"));
            wall.setProfile_id(rs.getInt("profile_id"));
            wall.setPermission(Wall.Permission.valueOf(rs.getString("permission").toUpperCase()));
            wall.setDeleted(rs.getBoolean("deleted"));
            walls.add(wall);
        }
        return walls;
    }


    @Override
    public Wall getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Wall> getDestinationByTaskId(Connection connection, int id) throws SQLException {
        return select(connection, SQL_GET_DESTINATION_BY_TASK_ID_QUERY, id);
    }

    @Override
    public List<Wall> getSourceByTaskId(Connection connection, int id) throws SQLException {
        return select(connection, SQL_GET_SOURCE_BY_TASK_ID_QUERY, id);
    }

    @Override
    public boolean deleteByProfileId(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETED_BY_PROFILE_ID_QUERY, id);
    }

}
