package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.OwnerDAO;
import com.epam.lab.spider.model.db.entity.Owner;

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

    @Override
    public Owner getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return odao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
