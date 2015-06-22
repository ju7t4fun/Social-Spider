package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.UserDAO;
import com.epam.lab.spider.model.db.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class UserDAOImp extends BaseDAO implements UserDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO user (name, surname, email, password, role, state, " +
            "deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE user SET name = ?, surname = ?, email = ?, password = ?, " +
            "role = ?, state = ?, deleted = ? WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE user SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM user WHERE deleted = false";
    private static final String SQL_VALIDATE_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE email = ? " +
            " AND password = ? AND deleted = false";
    private static final String SQL_GET_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email=? AND deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM user WHERE id = ? AND deleted = false";
    private static final String SQL_CHECK_PASSWORD_QUERY = "SELECT * FROM user WHERE email = ? AND password = ? AND " +
            "deleted = false";
    private static final String SQL_UPDATE_BY_COL_NAME_QUERY = "UPDATE user SET {name} = ? WHERE id = ?";

    @Override
    public boolean insert(Connection connection, User user) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString().toUpperCase(),
                user.getState().toString().toUpperCase(),
                user.getDeleted());
        user.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, User user) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString().toUpperCase(),
                user.getState().toString().toUpperCase(),
                user.getDeleted(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<User> select(Connection connection, String query, Object... args) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        User user;
        while (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setCreateTime(rs.getTimestamp("create_time"));
            System.out.println(rs.getString("role"));
            user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
            user.setState(User.State.valueOf(rs.getString("state").toUpperCase()));
            user.setDeleted(rs.getBoolean("deleted"));
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    //should remake later
    @Override
    public List<User> getWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = selectQuery(connection, SQL_SOME_QUERY);
        User user;
        while (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setState(User.State.valueOf(rs.getString("state").toUpperCase()));
            users.add(user);
        }
        return users;
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

    @Override
    public User getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    public User getByEmail(Connection connection, String email) throws SQLException {
        return first(select(connection, SQL_GET_BY_EMAIL_QUERY, email));
    }

    @Override
    public boolean checkPassword(Connection connection, String email, String password) throws SQLException {
        return first(select(connection, SQL_CHECK_PASSWORD_QUERY, email, password)) != null;
    }

    @Override
    public boolean updateByParameter(Connection connection, String name, String value, Integer id) throws SQLException {
        String replaceQuery = SQL_UPDATE_BY_COL_NAME_QUERY.replace("{name}", name);
        return changeQuery(connection, replaceQuery, value, id);
    }

    public User getByEmailAndPass(Connection connection, String email, String password) throws SQLException {
        return first(select(connection, SQL_VALIDATE_BY_EMAIL_AND_PASSWORD_QUERY, email, password));
    }

}
