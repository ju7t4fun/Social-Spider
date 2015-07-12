package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.EventDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sasha on 12.07.2015.
 */
public class EventServiceTest {

    Connection connection;
    Event event;
    private static final DAOFactory factory = DAOFactory.getInstance();
    private static final EventDAO edao = factory.create(EventDAO.class);

    @Before
    public void setUp() throws Exception {
        connection = PoolConnection.getConnection();
        connection.setAutoCommit(false);
        event = new Event();
        event.setUserId(1);
        event.setType(Event.Type.valueOf("WARN"));
        event.setTitle("TestTitle");
        event.setMessage("TestMessage");
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = edao.insert(connection, event);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        edao.insert(connection, event);
        Event newEvent = new Event();
        newEvent.setUserId(1);
        newEvent.setTitle("NewTitle");
        newEvent.setMessage("NewMessage");
        newEvent.setTime(new Date());
        Boolean check = edao.update(connection, 1, newEvent);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        edao.insert(connection, event);
        edao.delete(connection, 1);
        Event deletedEvent = edao.getById(connection, 1);
        assertNull(deletedEvent);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Event> events = edao.getAll(connection);
        assertTrue(events.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        List<Event> events = edao.getByUserId(connection, 1);
        assertTrue(events.size() > 0);
    }
}