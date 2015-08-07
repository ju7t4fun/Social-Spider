package com.epam.lab.spider.model.db;

import com.epam.lab.spider.persistence.PoolConnection;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Oleksandra Lobanok
 */
public class PoolConnectionTest {

    @Test
    public void testGetConnection() throws Exception {
        assertNotNull(PoolConnection.getConnection());
    }
}