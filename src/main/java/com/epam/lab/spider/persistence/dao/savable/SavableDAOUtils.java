package com.epam.lab.spider.persistence.dao.savable;

import com.epam.lab.spider.controller.utils.validation.ValidateResult;
import com.epam.lab.spider.controller.utils.validation.Validator;
import com.epam.lab.spider.model.EntitySynchronized;
import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.persistence.dao.CRUD;
import com.epam.lab.spider.persistence.dao.mysql.BaseDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.exception.SavableTransactionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public class SavableDAOUtils {
    public static final boolean VALIDATION_SUPPORT = true;
    private static final Logger LOG = Logger.getLogger(SavableDAOUtils.class);

    public static <E extends PersistenceIdentifiable> boolean safeSave(Connection conn, E entity, BaseDAO dao) {
        try {
            return save(conn,entity,dao);
        } catch (InvalidEntityException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        } catch (UnsupportedDAOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        } catch (ResolvableDAOException e) {
            LOG.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    public static <E extends PersistenceIdentifiable> boolean save(Connection conn, E entity, BaseDAO dao) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        EntitySynchronized<E> entitySynchronized = null;
        if (entity == null) return false;
        if (entity instanceof EntitySynchronized) {
            entitySynchronized = (EntitySynchronized<E>) entity;
            if (entitySynchronized.isSynchronized()) return true;
        } else {
            LOG.warn("Detected Object without Entity Synchronized.");
        }
        if (VALIDATION_SUPPORT) {
            Validator validator = new Validator();
            ValidateResult validateResult = validator.valideteWithResult(entity);
            if(!validateResult.isValid()){
                InvalidEntityException exception = new InvalidEntityException("Invalid entity");
                exception.setValidateResult(validateResult);
                throw exception;
            }
        }
        CRUD crudDAO;
        try{
            crudDAO = (CRUD) dao;
        }catch (ClassCastException x){
            throw new UnsupportedDAOException(""+dao.getClass().getName()+" must implement "+CRUD.class.getName(),x);
        }
        boolean result ;
        try{
            try {
                Integer index = entity.getId();
                if (index != null) {
                    return crudDAO.update(conn, index, entity);
                } else {
                    result = crudDAO.insert(conn, entity);
                    if (!result) throw new SavableTransactionException();
                    return true;
                }
            } finally {
                if (entitySynchronized != null) entitySynchronized.setSynchronized();
            }
        }catch (SQLException x){
            throw new ResolvableDAOException(x);
        }
    }


    public static <E extends PersistenceIdentifiable> boolean saveFromInterface(java.sql.Connection conn, E entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        BaseDAO dao = DAOFactory.getInstance().getCrudDAOByEntity(entity.getClass());
        return SavableDAOUtils.save(conn, entity, dao);
    }

}
