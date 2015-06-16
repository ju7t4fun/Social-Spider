package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.ProfileDAO;
import com.epam.lab.spider.model.db.dao.WallDAO;
import com.epam.lab.spider.model.db.entity.Profile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;


/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class ProfileService implements BaseService<Profile> {

    private static DAOFactory factory = DAOFactory.getInstance();
    private static ProfileDAO pdao = factory.create(ProfileDAO.class);
    private static WallDAO wdao = factory.create(WallDAO.class);

    @Override
    public boolean insert(Profile profile) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.insert(connection, profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Profile profile) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.update(connection, id, profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                assertTransaction(wdao.deleteByProfileId(connection, id));
                assertTransaction(pdao.delete(connection, id));
                connection.commit();
            } catch (SQLTransactionException e) {
                connection.rollback();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Profile> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Profile getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Profile getByVkId(int vkId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getByVkId(connection, vkId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Profile> getByUserId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pdao.getByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isByVkId(int id) {
        return getByVkId(id) != null;
    }

}
