package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sasha on 12.07.2015.
 */
public class MessageServiceTest {

    Message message;
    MessageService ms = new MessageService();

    @Before
    public void setUp() throws Exception {
        message = new Message();
        message.setUserId(1);
        message.setText("TestText");
        message.setType(Message.Type.TO_USER);
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = ms.insert(message);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        ms.insert(message);
        Message newMessage = new Message();
        newMessage.setUserId(1);
        newMessage.setText("newText");
        newMessage.setType(Message.Type.TO_USER);
        Boolean check = ms.update(1,newMessage);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        ms.insert(message);
        ms.delete(1);
        Message deletedMessage = ms.getById(1);
        assertTrue(deletedMessage.getDeleted());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Message> messages = ms.getAll();
        assertTrue(messages.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Message newMessage = ms.getById(1);
        assertNotNull(newMessage);
    }

    @Test
    public void testGetCountUnReadByUserId() throws Exception {
        Integer count = ms.getCountUnReadByUserId(1);
        assertNotNull(count);
    }
}