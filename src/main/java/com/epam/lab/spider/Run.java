package com.epam.lab.spider;

import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.dao.mysql.DAOFactoryImp;

import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {

    public static void main(String[] args) throws SQLException {
        DAOFactory factory = new DAOFactoryImp();
        BaseDAO dao = (BaseDAO) factory.create(UserDAO.class);
        System.out.println(dao);
    }

}
