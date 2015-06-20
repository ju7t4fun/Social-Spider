package com.epam.lab.spider.model.db.service.savable;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by shell on 6/17/2015.
 * Like if you understand true value of name this interface.
 */
public interface SavableService<E> {
    default boolean save(E entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                return save(entity, connection);
            } catch (SQLTransactionException x) {
                connection.rollback();
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }


            }
        } catch (SQLException x) {

            x.printStackTrace();
            return false;
        }
    }

    boolean save(E entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException;

}
