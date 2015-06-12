package com.epam.lab.spider.model;

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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vk_spider", "root", "1111");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
