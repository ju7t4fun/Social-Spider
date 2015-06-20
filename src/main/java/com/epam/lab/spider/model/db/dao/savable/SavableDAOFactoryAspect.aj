package com.epam.lab.spider.model.db.dao.savable;

import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shell on 6/18/2015.
 */
public aspect SavableDAOFactoryAspect {
    public <E> SavableDAO<E> DAOFactory.getSavableDAO(Class<E> clazz) {
        CRUD localDAO = this.getCrudDAOByEntity(clazz);
        if(localDAO instanceof SavableDAO){
            return (SavableDAO<E>) localDAO;
        }
        return null;
    }
/*    public static  final Logger LOG = Logger.getLogger(DAOFactorySavable.class);

    pointcut toString(): execution(* *.toString(..) );

    pointcut someExec(Object o): execution( * *.*(..)) && target(o) && ! toString() ;
    after(Object o):someExec(o){
        LOG.debug(thisJoinPointStaticPart);
        //LOG.debug("Initialization DAO factory");

    }
    after(Object o):call(* *.put(..))&& target(o) && ! toString() {
        LOG.debug(thisJoinPointStaticPart);
        LOG.debug(o.getClass());
    }


    private static Map<Class<?>, BaseDAO> DAOFactory.daoSavableMap = new HashMap<>();*/

}
