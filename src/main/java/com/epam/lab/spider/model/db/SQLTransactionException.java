package com.epam.lab.spider.model.db;

import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class SQLTransactionException extends SQLException {

    public static void assertTransaction(boolean result) throws SQLTransactionException {
        if (!result) throw new SQLTransactionException();
    }

}
