package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.FilterDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.service.savable.SavableService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.epam.lab.spider.model.db.SQLTransactionException.assertTransaction;

/**
 * Created by Sasha on 12.06.2015.
 */
public class FilterService implements BaseService<Filter>, SavableService {

    private DAOFactory factory = DAOFactory.getInstance();
    private FilterDAO fdao = factory.create(FilterDAO.class);


    @Override
    public boolean save(Object entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return  SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Object entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.customSave(conn, entity);
    }
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
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);

                assertTransaction(fdao.delete(connection, id));
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
