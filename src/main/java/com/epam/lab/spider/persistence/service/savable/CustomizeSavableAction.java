package com.epam.lab.spider.persistence.service.savable;

import java.sql.SQLException;

/**
 * @author Yura Kovalik
 */
public interface CustomizeSavableAction {

    void action(Object entity) throws SQLException;
}
