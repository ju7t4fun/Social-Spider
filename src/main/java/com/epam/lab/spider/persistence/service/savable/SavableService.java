package com.epam.lab.spider.persistence.service.savable;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;

import java.sql.Connection;

/**
 * This interface destined to unification all service base insert and update method.
 *
 * @author Yura Kovalik
 */
public interface SavableService<E extends PersistenceIdentifiable> {

    boolean save(E entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException;

    boolean save(E entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException;

}
