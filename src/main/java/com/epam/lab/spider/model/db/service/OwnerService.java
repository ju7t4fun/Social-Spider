package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.OwnerDAO;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class OwnerService implements BaseService<Owner> {

    private DAOFactory factory = DAOFactory.getInstance();
    private OwnerDAO odao = factory.create(OwnerDAO.class);

    @Override
    public boolean insert(Owner owner) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.insert(connection, owner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Owner owner) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.update(connection, id, owner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Owner> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Owner> getLimited(Integer begin, Integer end) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getLimited(connection, begin, end);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Owner getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Owner getByVkId(int vk_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getByVkId(connection, vk_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Owner> getWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getCountWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Owner> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getByUserId(connection, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Owner> getByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getByUserId(connection, id, page, size);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getCountByUserId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
