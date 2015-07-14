package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sasha on 12.07.2015.
 */
public class UserServiceTest {

    User user;
    UserService us = new UserService();

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(5);
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
        User newUser = new User();
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