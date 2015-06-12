package com.epam.lab.spider;

import com.epam.lab.spider.model.ConnectionManager;
import com.epam.lab.spider.model.dao.mysql.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.dao.mysql.DAOFactoryImp;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        DAOFactory factory = new DAOFactoryImp();
        BaseDAO dao = (BaseDAO) factory.create(UserDAO.class);
        
    }

}
