package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.User;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Oleksandra Lobanok
 */
public class UserServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    User user;
    UserService us = new UserService();

    @Before
    public void setUp() throws Exception {
        user = ENTITY_FACTORY.createUser();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("test@test.ua");
        user.setPassword("testpw");
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = us.insert(user);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        us.insert(user);
        User newUser = ENTITY_FACTORY.createUser();
        newUser.setEmail("test@test.ua");
        newUser.setPassword("testpw");
        Boolean check = us.update(user.getId(),newUser);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        us.insert(user);
        us.delete(user.getId());
        User deletedUser = us.getById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = us.getAllUser();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetByEmail() throws Exception {
        us.insert(user);
        User newUser = us.getByEmail("test@test.ua");
        assertEquals("test@test.ua", newUser.getEmail());
    }

    @Test
    public void testGetById() throws Exception {
        User newUser = us.getById(1);
        assertNotNull(newUser);
    }

    @Test
    public void testCheckPassword() throws Exception {
        us.insert(user);
        Boolean check = us.checkPassword("test@test.ua", "testpw");
        assertTrue(check);
    }
}