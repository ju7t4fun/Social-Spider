package com.epam.lab.spider.model.db.service.savable;

import java.sql.SQLException;

/**
 * Created by shell on 6/26/2015.
 */
public interface CustomizeSavableAction {

    public void action(Object entity) throws SQLException;
}
