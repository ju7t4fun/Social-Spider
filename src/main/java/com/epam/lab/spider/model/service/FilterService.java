package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.FilterDAO;
import com.epam.lab.spider.model.entity.Filter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class FilterService implements BaseService<Filter> {

    private DAOFactory factory = DAOFactory.getInstance();
    private FilterDAO fdao = factory.create(FilterDAO.class);

    @Override
    public boolean insert(Filter filter) {
        try (Connection connection = PoolConnection.getConnection()) {
            return fdao.insert(connection, filter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Filter filter) {
        try (Connection connection = PoolConnection.getConnection()) {
            return fdao.update(connection, id, filter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return fdao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Filter> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return fdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Filter getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return fdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
