package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.UserDAO;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sasha on 12.07.2015.
 */
public class UserServiceTest {

    User user;
    Connection connection;
    private DAOFactory factory = DAOFactory.getInstance();
    private UserDAO udao = factory.create(UserDAO.class);

    @Before
    public void setUp() throws Exception {
        connection = PoolConnection.getConnection();
        connection.setAutoCommit(false);
        user = new User();
        user.setId(5);
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("test@test.ua");
        user.setPassword("testpw");
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = udao.insert(connection, user);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        udao.insert(connection, user);
        User newUser = new User();
        newUser.setEmail("test@test.ua");
        newUser.setPassword("b0baee9d279d34fa1dfd71aadb908c3f");
        Boolean check = udao.update(connection, user.getId(),newUser);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        udao.insert(connection, user);
        udao.delete(connection, user.getId());
        User deletedUser = udao.getById(connection, user.getId());
        assertNull(deletedUser);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = udao.getAllUser(connection);
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetByEmail() throws Exception {
        udao.insert(connection, user);
        User newUser = udao.getByEmail(connection, "test@test.ua");
        assertEquals("test@test.ua", newUser.getEmail());
    }

    @Test
    public void testGetById() throws Exception {
        User newUser = udao.getById(connection, 1);
        assertNotNull(newUser);
    }

    @Test
    public void testCheckPassword() throws Exception {
        udao.insert(connection, user);
        Boolean check = udao.checkPassword(connection, "test@test.ua", "testpw");
        assertTrue(check);
    }
}