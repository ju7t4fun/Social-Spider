package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class UserDAOImp extends BaseDAO implements UserDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO user (name, surname, email, password, role_id, " +
            "deleted, confirm) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE user SET name = ?, surname = ?, email = ?, password = ?, " +
            "role_id = ?, deleted = ?, confirm = ? WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE user SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM user WHERE deleted = false";
    private static final String SQL_VALIDATE_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM user WHERE email=? " +
            " AND password=? AND deleted = false";
    private static final String SQL_GET_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email=? AND deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM user WHERE id = ? AND deleted = false";
    private static final String SQL_CHECK_PASSWORD_QUERY = "SELECT * FROM user WHERE email = ? AND password = ? AND " +
            "deleted = false";

    @Override
    public boolean insert(Connection connection, User user) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId(),
                user.getDeleted(),
                user.getConfirm());
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
                user.getRole().getId(),
                user.getDeleted(),
                user.getConfirm(),
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
            user.setRole(User.Role.getById(rs.getInt("role_id")));
            user.setDeleted(rs.getBoolean("deleted"));
            user.setConfirm(rs.getBoolean("confirm"));
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
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

    public User getByEmailAndPass(Connection connection, String email, String password) throws SQLException {
        return first(select(connection, SQL_VALIDATE_BY_EMAIL_AND_PASSWORD_QUERY, email, password));
    }
}
