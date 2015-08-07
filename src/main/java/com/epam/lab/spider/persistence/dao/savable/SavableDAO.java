package com.epam.lab.spider.persistence.dao.savable;

import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;

/**
 * This interface destined to unification all DAO base insert and update method.
 * @author Yura Kovalik
 */

public interface SavableDAO<E> {
    boolean save(java.sql.Connection conn, E entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException;
}
