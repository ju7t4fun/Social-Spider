package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Event;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.entity.impl.EventImpl;
import com.epam.lab.spider.persistence.service.EventService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleksandra Lobanok
 */
public class EventServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    Event event;
    EventService es = new EventService();

    @Before
    public void setUp() throws Exception {
        event = ENTITY_FACTORY.createEvent();
        event.setUserId(1);
        event.setType(EventImpl.Type.valueOf("WARN"));
        event.setTitle("TestTitle");
        event.setMessage("TestMessage");
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = es.insert(event);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        es.insert(event);
        Event newEvent = ENTITY_FACTORY.createEvent();
        newEvent.setUserId(1);
        newEvent.setTitle("NewTitle");
        newEvent.setMessage("NewMessage");
        newEvent.setTime(new Date());
        Boolean check = es.update(10, newEvent);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        es.insert(event);
        es.delete(1);
        Event deletedEvent = es.getById(1);
        assertNull(deletedEvent);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Event> events = es.getAll();
        assertTrue(events.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        List<Event> events = es.getByUserId(1);
        assertTrue(events.size() > 0);
    }
}