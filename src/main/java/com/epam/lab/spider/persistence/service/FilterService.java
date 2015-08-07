package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.FilterDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */
public class FilterService implements BaseService<Filter>, SavableService<Filter> {
    private static final Logger LOG = Logger.getLogger(FilterService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private FilterDAO filterDAO = factory.create(FilterDAO.class);


    @Override
    public boolean save(Filter entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return  SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Filter entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.customSave(conn, entity);
    }
    @Override
    public boolean insert(Filter filter) {
        try (Connection connection = PoolConnection.getConnection()) {
            return filterDAO.insert(connection, filter);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Filter filter) {
        try (Connection connection = PoolConnection.getConnection()) {
            return filterDAO.update(connection, id, filter);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);

                SQLTransactionException.assertTransaction(filterDAO.delete(connection, id));
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
            LOG.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public List<Filter> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return filterDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Filter getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return filterDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }


}
