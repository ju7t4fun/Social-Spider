package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.FilterService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * @author Oleksandra Lobanok
 */
public class FilterServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    Filter filter;
    FilterService fs = new FilterService();

    @Before
    public void setUp() throws Exception {
        filter = ENTITY_FACTORY.createFilter();
        filter.setLikes(100);
        filter.setRePosts(10);
        filter.setComments(1);
    }

    @Test
    public void testSave() throws Exception {
        assertTrue(fs.save(filter));
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = fs.insert(filter);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        Filter newFilter = ENTITY_FACTORY.createFilter();
        newFilter.setLikes(80);
        newFilter.setRePosts(50);
        newFilter.setComments(30);
        fs.update(3, newFilter);
        Filter updatedFilter = fs.getById(3);
        assertEquals((Integer) 80, updatedFilter.getLikes());
    }

    @Test
    public void testDelete() throws Exception {
        fs.delete(1);
        Filter deletedFilter = fs.getById(1);
        assertNull(deletedFilter);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Filter> filters = fs.getAll();
        assertTrue(filters.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Filter newFilter = fs.getById(3);
        assertNotNull(newFilter);
    }
}