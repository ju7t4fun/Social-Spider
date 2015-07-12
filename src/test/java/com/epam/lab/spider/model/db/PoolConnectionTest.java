package com.epam.lab.spider.model.db;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Sasha on 12.07.2015.
 */
public class PoolConnectionTest {

    @Test
    public void testGetConnection() throws Exception {
        assertNotNull(PoolConnection.getConnection());
    }
}