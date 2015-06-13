package com.epam.lab.spider.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 18.04.2015.
 */
public class ConnectionManager {

    private static Connection connection = null;

    private ConnectionManager() {
        super();
    }

    public static Connection getConnection() {
        if (connection == null) {
            initialization();
        }
        return connection;
    }

    private static void initialization() {
        try {
            Context initCtx = null;
            try {
                initCtx = new InitialContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            Context envCtx = null;
            try {
                envCtx = (Context) initCtx.lookup("java:comp/env");
            } catch (NamingException e) {
                e.printStackTrace();
            }

            // Look up our data source
            DataSource ds = null;
            try {
                ds = (DataSource) envCtx.lookup("jdbc/vk_spider");
            } catch (NamingException e) {
                e.printStackTrace();
            }

            // Allocate and use a connection from the pool
            connection = ds.getConnection();


            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vk_spider", "dzyuba", "1111");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
