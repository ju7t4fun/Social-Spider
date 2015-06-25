package com.epam.lab.spider.model.db.service.savable;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by shell on 6/19/2015.
 */
public class SavableServiceUtil<E> {
    private static DAOFactory daoFactory = DAOFactory.getInstance();
    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    public static boolean safeSave(Object o){
        try{
            SavableService savableService = serviceFactory.getSavableService(o.getClass());
            savableService.save(o);
            return true;
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
                        ///////LOG
                    }
                    else list.add(collElement);
                }
            }else{
                list.add(object);
            }
        }
        return list;
    }

    public static boolean customSave(Connection connection,Object entity, Object[]before, Object[] after)
            throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        List beforeEntityList = unpack(before);
        List afterEntityList = unpack(after);

        for(Object innerEntity:beforeEntityList){
            if(innerEntity!=null){
                SavableService service = serviceFactory.getSavableService(innerEntity.getClass());
                service.save(innerEntity,connection);
            }
        }
        SavableDAO savableDAO = daoFactory.getSavableDAO(entity.getClass());
        savableDAO.save(connection, entity);
        for(Object innerEntity:afterEntityList){
            if(innerEntity!=null){
                SavableService service = serviceFactory.getSavableService(innerEntity.getClass());
                service.save(innerEntity,connection);
            }
        }
         return true;
    }

}
