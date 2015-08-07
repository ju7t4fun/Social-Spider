package com.epam.lab.spider.persistence.service.savable;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.SQLTransactionException;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.SavableDAO;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.savable.exception.SavableTransactionException;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Yura Kovalik
 */
public class SavableServiceUtils {
    public static final Logger LOG = Logger.getLogger(SavableServiceUtils.class);
    private static DAOFactory daoFactory = DAOFactory.getInstance();
    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();

    public static <E extends PersistenceIdentifiable> boolean safeSave(E o) {
        try{
            SavableService savableService = serviceFactory.getSavableService(o.getClass());
            return savableService.save(o);
        }catch (Exception x){
            x.printStackTrace();
            return false;
        }
    }

    private static List<PersistenceIdentifiable> unpack(Object[] array) {
        List<PersistenceIdentifiable> list = new ArrayList();
        for(Object object:array){
            if(object instanceof Collection){
                Collection collection = (Collection) object;
                for(Object collElement:collection){
                    if(collElement instanceof Collection){
                        LOG.error("To many recursion");
                    } else if (collElement instanceof PersistenceIdentifiable) {
                        list.add((PersistenceIdentifiable) collElement);
                    }
                }
            } else if (object instanceof PersistenceIdentifiable) {
                list.add((PersistenceIdentifiable) object);
            }
        }
        return list;
    }

    public static <E extends PersistenceIdentifiable> boolean customSave(Connection connection, E entity)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return customSave(connection,entity,null,null,null);
    }

    public static <E extends PersistenceIdentifiable> boolean customSave(Connection connection, E entity, Object[] before, Object[] after)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return customSave(connection,entity,before,after,null);
    }


    public static <E extends PersistenceIdentifiable> boolean customSave(Connection connection, E entity, Object[] before, Object[] after, CustomizeSavableAction[] actions)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        boolean result ;
        List<PersistenceIdentifiable> beforeEntityList = null;
        List<PersistenceIdentifiable> afterEntityList = null;
        if(before!=null)
        beforeEntityList = unpack(before);
        if(after!=null)
        afterEntityList = unpack(after);
        if(beforeEntityList!=null)
            for (PersistenceIdentifiable innerEntity : beforeEntityList) {
            if(innerEntity!=null){
                SavableService service = serviceFactory.getSavableService(innerEntity.getClass());
                service.save(innerEntity,connection);
            }
        }
        SavableDAO savableDAO = daoFactory.getSavableDAO(entity.getClass());
        result = savableDAO.save(connection, entity);
        if(afterEntityList!=null)
            for (PersistenceIdentifiable innerEntity : afterEntityList) {
            if(innerEntity!=null){
                SavableService service = serviceFactory.getSavableService(innerEntity.getClass());
                service.save(innerEntity,connection);
            }
        }
        if(actions!=null)
        for(CustomizeSavableAction action:actions){
            try {
                action.action(entity);
            }catch (SQLException x){
                LOG.error(x);
            }
        }
        return result;
    }

    public static <E extends PersistenceIdentifiable> boolean saveFromInterface(E entity, SavableService savableService) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException
    {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                return savableService.save(entity, connection);
            } catch (SQLTransactionException|SavableTransactionException x) {
                connection.rollback();
                LOG.error("SQL ROLLBACK",x);
                return false;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        } catch (SQLException x) {
            LOG.error("SQL ROLLBACK", x);
            return false;
        }
    }


}
