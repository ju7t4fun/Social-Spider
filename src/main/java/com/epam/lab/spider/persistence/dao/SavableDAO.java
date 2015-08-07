package com.epam.lab.spider.persistence.dao;


import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;

import java.sql.Connection;

/**
 * @author Yura Kovalik
 * Like if you understand true value of name this interface.
 */
public interface SavableDAO<E> {
    boolean VALIDATION_SUPPORT = true;

    boolean save(Connection conn, E e) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException;
}
