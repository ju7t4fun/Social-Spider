package com.epam.lab.spider.model.db.service;


import com.epam.lab.spider.model.db.entity.Category;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sasha on 12.07.2015.
 */
public class CategoryServiceTest {

    Category category;
    CategoryService cs  = new CategoryService();

    @Before
    public void setUp() throws Exception {
        category = new Category();
        category.setId(47);
        category.setName("testname");
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        Boolean check = cs.insert(category);
        assertTrue(check);
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        Category newCategory = new Category();
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