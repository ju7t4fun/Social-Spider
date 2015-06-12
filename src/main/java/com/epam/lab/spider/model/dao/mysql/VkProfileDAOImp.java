package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.VkProfileDAO;
import com.epam.lab.spider.model.entity.VkProfile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class VkProfileDAOImp extends BaseDAO implements VkProfileDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO vk_profile (user_id, vk_id, access_token, ext_time) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE vk_profile SET user_id = ?, vk_id = ?, access_token = ?, " +
            "ext_time = ? WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM vk_profile WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM vk_profile";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM vk_profile WHERE id = ?";

    @Override
    public boolean insert(Connection connection, VkProfile profile) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, profile.getUserId(), profile.getVkId(), profile
                .getAccessToken(), profile.getExtTime());
    }

    @Override
    public boolean update(Connection connection, int id, VkProfile profile) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, profile.getUserId(), profile.getVkId(), profile
                .getAccessToken(), profile.getExtTime(), id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<VkProfile> select(Connection connection, String query, Object... args) throws SQLException {
        List<VkProfile> profiles = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        VkProfile profile;
        while (rs.next()) {
            profile = new VkProfile();
            profile.setId(rs.getInt("id"));
            profile.setUserId(rs.getInt("user_id"));
            profile.setVkId(rs.getInt("vk_id"));
            profile.setAccessToken(rs.getString("access_token"));
            profile.setExtTime(rs.getDate("ext_time"));
            profiles.add(profile);
        }
        return profiles;
    }

    @Override
    public List<VkProfile> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public VkProfile getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }
}
