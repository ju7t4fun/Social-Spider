package com.epam.lab.spider.model.db.dao;

import com.mysql.jdbc.Connection;

/**
 * Created by shell on 6/17/2015.
 * Like if you understand true value of name this interface.
 */
public interface SavableDAO<E> {
    boolean VALIDATION_SUPPORT = true;
    boolean save(Connection conn,E e);
}
