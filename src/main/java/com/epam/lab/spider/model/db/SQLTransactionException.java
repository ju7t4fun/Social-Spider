package com.epam.lab.spider.model.db;

import java.sql.SQLException;

/**
 * Created by Boyarsky Vitaliy on 16.06.2015.
 */
public class SQLTransactionException extends SQLException {
    // я хз що цей ассерт мав зробити
    // але щоб вивадяти звязки при оновленні потрібно
    // або зчитувати обєкти знову і дивитись що змінилось
    // або мати в памяти граф обєктів які були витягнені
    public static void assertTransaction(boolean result) throws SQLTransactionException {
        if (!result) throw new SQLTransactionException();
    }

}
