package com.epam.lab.spider.model.db.service;


import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Category;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.entity.impl.CategoryImpl;
import com.epam.lab.spider.persistence.service.CategoryService;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleksandra Lobanok
 */
public class CategoryServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    Category category;
    CategoryService cs  = new CategoryService();

    @Before
    public void setUp() throws Exception {
        category = ENTITY_FACTORY.createCategory();
        category.setName("testname");
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        Boolean check = cs.insert(category);
        assertTrue(check);
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        Category newCategory = new CategoryImpl();
        newCategory.setName("newname");
        cs.insert(category);
        Boolean check = cs.update(1, newCategory);
        assertTrue(check);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        cs.insert(category);
        cs.delete(category.getId());
        Category deletedCategory = cs.getById(category.getId());
        assertNull(deletedCategory);
    }

    @org.junit.Test
    public void testGetAll() throws Exception {
        List<Category> categories = cs.getAll();
        assertTrue(categories.size() > 0);
    }

    @org.junit.Test
    public void testGetCountAll() throws Exception {
        Integer count = cs.getCountAll();
        assertTrue(count > 0);
    }
}