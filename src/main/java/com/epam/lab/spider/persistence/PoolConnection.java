package com.epam.lab.spider.persistence;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Boyarsky Vitaliy
 */
public class PoolConnection {
    public static final Logger LOG = Logger.getLogger(PoolConnection.class);
    private static PoolConnection instance = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.fatal("WG WTF?");
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    private DataSource dataSource;

    private PoolConnection() {
        super();
        dataSource = init();
    }

    protected static PoolConnection getInstance() {
        if (instance == null) {
            synchronized (PoolConnection.class) {
                if (instance == null) {
                    instance = new PoolConnection();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        try {
            return getInstance().dataSource.getConnection();
        } catch (SQLException | RuntimeException x) {
            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vk_spider?useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");
                if (connection != null)
                    LOG.error("Незнайдено налаштувань пулу. Повернуто нульовий обєкт. Створення одиночного зєднання!");
                else LOG.fatal("NULL SIMPLE CONNECTION #1:LOCAL!");
                return connection;
            } catch (Throwable t1) {
                LOG.fatal("NULL SIMPLE CONNECTION #1:LOCAL!" + t1.getClass().getName() + ":" + t1.getLocalizedMessage());
                try {
                    return DriverManager.getConnection("jdbc:mysql://${OPENSHIFT_MYSQL_DB_HOST}:${OPENSHIFT_MYSQL_DB_PORT}/social?useUnicode=true&amp;characterEncoding=UTF-8", "adminyMTMGsd", "tqEZA-ktwAAp");
                } catch (Throwable t2) {
                    LOG.fatal("NULL SIMPLE CONNECTION #2:SOFT CODE!" + t2.getClass().getName() + ":" + t2.getLocalizedMessage());
                    try {
                        return DriverManager.getConnection("jdbc:mysql://127.5.166.130:3306/social?useUnicode=true&amp;characterEncoding=UTF-8", "adminyMTMGsd", "tqEZA-ktwAAp");
                    } catch (Throwable t3) {
                        LOG.fatal("NULL SIMPLE CONNECTION #3:HARD CODED!" + t3.getClass().getName() + ":" + t3.getLocalizedMessage());
                        throw new NullPointerException();
                    }
                }
            }

        }
    }

    private static DataSource init() {
        DataSource ds = null;
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:jboss/datasources/MySQLDS");
            return ds;
        } catch (NamingException e) {
            LOG.error(e.getMessage());
        }
        if (ds == null) {
            try {
                InitialContext ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySQLDS");
                return ds;
            } catch (NamingException e) {
                LOG.error(e.getMessage());
            }
        }
        if (ds == null) {
            try {
                InitialContext ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/vk_spider");
                return ds;
            } catch (NamingException e) {
                LOG.error(e.getMessage());
            }
        }
        return null;
    }

}
