package com.epam.lab.spider;


import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.mysql.UserActionsDAOImpl;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * @author Boyarsky Vitaliy
 */
public class Run {
    public static final Logger LOG = Logger.getLogger(Run.class);

    public static int fib(int number) {
        if (number <= 2) return number;
        return fib(number - 1) + fib(number - 2);
    }

    public static void main(String[] args) throws VKException, SQLException {
        UserActionsDAOImpl userActionsDAO = new UserActionsDAOImpl();
        userActionsDAO.createTable(PoolConnection.getConnection());
    }
}

