package com.epam.lab.spider.model.db.service.savable;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.SQLTransactionException;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by shell on 6/19/2015.
 */
public class SavableServiceUtil<E> {
    public static final Logger LOG = Logger.getLogger(SavableServiceUtil.class);
    private static DAOFactory daoFactory = DAOFactory.getInstance();
    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    public static boolean safeSave(Object o){
        try{
            SavableService savableService = serviceFactory.getSavableService(o.getClass());
            return savableService.save(o);
        }catch (Exception x){
            x.printStackTrace();
            return false;
        }
    }




    private static List unpack(Object[] array){
        List list = new ArrayList();
        for(Object object:array){
            if(object instanceof Collection){
                Collection collection = (Collection) object;
                for(Object collElement:collection){
                    if(collElement instanceof Collection){
                        LOG.error("To many recursion");
                    }
                    else list.add(collElement);
                }
            }else{
                list.add(object);
            }
        }
        return list;
    }

    public static boolean customSave(Connection connection,Object entity)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException{
        return customSave(connection,entity,null,null,null);
    }
    public static boolean customSave(Connection connection,Object entity, Object[]before, Object[] after)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException{
        return customSave(connection,entity,before,after,null);
    }


    public static boolean customSave(Connection connection,Object entity, Object[]before, Object[] after, CustomizeSavableAction[] actions)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {

        List beforeEntityList = null;
        List afterEntityList = null;
        if(before!=null)
        beforeEntityList = unpack(before);
        if(after!=null)
        afterEntityList = unpack(after);
        if(beforeEntityList!=null)
        for(Object innerEntity:beforeEntityList){
            if(innerEntity!=null){
                SavableService service = serviceFactory.getSavableService(innerEntity.getClass());
                service.save(innerEntity,connection);
            }
        }
        SavableDAO savableDAO = daoFactory.getSavableDAO(entity.getClass());
        savableDAO.save(connection, entity);
        if(afterEntityList!=null)
        for(Object innerEntity:afterEntityList){
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
        return true;
    }

    public static boolean saveFromInterface(Object entity, SavableService savableService ) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException
    {
        try {
            Connection connection = PoolConnection.getConnection();
            try {
                connection.setAutoCommit(false);
                return savableService.save(entity, connection);
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


}
