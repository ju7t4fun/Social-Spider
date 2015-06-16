package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.ProfileDAO;
import com.epam.lab.spider.model.db.entity.Profile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class ProfileDAOImp extends BaseDAO implements ProfileDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO vk_profile (user_id, vk_id, access_token, ext_time, " +
            "app_id, deleted) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE vk_profile SET user_id = ?, vk_id = ?, access_token = ?, " +
            "ext_time = ?, app_id = ?, deleted = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE FROM vk_profile WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE vk_profile SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM vk_profile WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM vk_profile WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_VK_ID_QUERY = "SELECT * FROM vk_profile WHERE vk_id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM vk_profile WHERE user_id = ? AND deleted = " +
            "false";

    @Override
    public boolean insert(Connection connection, Profile profile) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                profile.getUserId(),
                profile.getVkId(),
                profile.getAccessToken(),
                profile.getExtTime(),
                profile.getAppId(),
                profile.getDeleted());
        profile.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Profile profile) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                profile.getUserId(),
                profile.getVkId(),
                profile.getAccessToken(),
                profile.getExtTime(),
                profile.getAppId(),
                profile.getDeleted(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Profile> select(Connection connection, String query, Object... args) throws SQLException {
        List<Profile> profiles = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Profile profile;
        while (rs.next()) {
            profile = new Profile();
            profile.setId(rs.getInt("id"));
            profile.setUserId(rs.getInt("user_id"));
            profile.setVkId(rs.getInt("vk_id"));
            profile.setAccessToken(rs.getString("access_token"));
            profile.setExtTime(rs.getDate("ext_time"));
            profile.setAppId(rs.getInt("app_id"));
            profile.setDeleted(rs.getBoolean("deleted"));
            profiles.add(profile);
        }
        return profiles;
    }

    @Override
    public List<Profile> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Profile getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Profile> getByUserId(Connection connection, int id) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, id);
    }

    @Override
    public Profile getByVkId(Connection connection, int vkId) throws SQLException {
        return first(select(connection, SQL_GET_BY_VK_ID_QUERY, vkId));
    }

}
