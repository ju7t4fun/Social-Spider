package com.epam.lab.spider.model.db.service;


import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.CategoryDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.Category;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sasha on 12.07.2015.
 */
public class CategoryServiceTest {

    Category category;
    Connection connection;

    private static final DAOFactory factory = DAOFactory.getInstance();
    private CategoryDAO cdao = factory.create(CategoryDAO.class);

    @Before
    public void setUp() throws Exception {
        connection = PoolConnection.getConnection();
        connection.setAutoCommit(false);
        category = new Category();
        category.setId(47);
        category.setName("testname");
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        Boolean check = cdao.insert(connection, category);
        assertTrue(check);
    }

    @org.junit.Test
    public void testUpdate() throws Exception {
        Category newCategory = new Category();
        newCategory.setName("newname");
        cdao.insert(connection, category);
        Boolean check = cdao.update(connection, 1, newCategory);
        assertTrue(check);
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        cdao.insert(connection, category);
        cdao.delete(connection, category.getId());
        Category deletedCategory = cdao.getById(connection, category.getId());
        assertNull(deletedCategory);
    }

    @org.junit.Test
    public void testGetAll() throws Exception {
        List<Category> categories = cdao.getAll(connection);
        assertTrue(categories.size() > 0);
    }

    @org.junit.Test
    public void testGetCountAll() throws Exception {
        Integer count = cdao.getCount(connection);
        assertTrue(count > 0);
    }
}