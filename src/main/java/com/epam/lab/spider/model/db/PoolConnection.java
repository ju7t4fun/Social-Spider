package com.epam.lab.spider.model.db;

import org.apache.log4j.Logger;

import javax.annotation.Resource;
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

    private DataSource dataSource;

    private static PoolConnection instance = null;

    protected static PoolConnection getInstance(){
        if(instance == null){
            synchronized (PoolConnection.class){
                if(instance == null){
                    instance = new PoolConnection();
                }
            }
        }
        return instance;
    }

    public static final Logger LOG = Logger.getLogger(PoolConnection.class);


    private PoolConnection() {
        super();
        dataSource = init();
    }

    public static Connection getConnection() throws SQLException {
        try {
            return getInstance().dataSource.getConnection();
        } catch (SQLException | RuntimeException x) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vk_spider?useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");
            if(connection != null)LOG.error("Незнайдено налаштувань пулу. Повернуто нульовий обєкт. Створення одиночного зєднання!");
            else LOG.fatal("Неможливо знайти джерело даних!");
            return connection;
        }
    }

    private static DataSource init() {
        DataSource ds = null;
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:jboss/datasources/MySQLDS");
        } catch (NamingException e) {
            LOG.error(e.getMessage());
        }
        if(ds == null){
            try {
                InitialContext ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/vk_spider");
            } catch (NamingException e) {
                LOG.error(e.getMessage());
            }
        }
        return ds;
    }

}
