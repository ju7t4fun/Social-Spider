package com.epam.lab.spider.model.db.dao.savable;


import com.epam.lab.spider.model.db.dao.CRUD;

import com.epam.lab.spider.model.db.dao.mysql.BaseDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import java.sql.Connection;
import org.apache.log4j.Logger;

/**
 * Created by shell on 6/17/2015.
 */
public aspect SavableDAOAspect {
/*    public boolean SavableDAO<E>.save(java.sql.Connection conn,E entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException{
        BaseDAO dao = (BaseDAO) DAOFactory.getInstance().getCrudDAOByEntity(entity.getClass());
        return SavableCRUDUtil.save(conn,entity, dao);
    }*/
}

