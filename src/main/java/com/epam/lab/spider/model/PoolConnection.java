package com.epam.lab.spider.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class PoolConnection {

    private static DataSource dataSource = null;

    private PoolConnection() {
        super();
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = init();
        }
        return dataSource.getConnection();
        //return DriverManager.getConnection("jdbc:mysql://localhost:3306/vk_spider", "root", "1111");
    }

    private static DataSource init() {
        DataSource ds = null;
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/vk_spider");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }

}
