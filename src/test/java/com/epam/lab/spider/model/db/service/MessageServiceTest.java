package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Message;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.MessageService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleksandra Lobanok
 */
public class MessageServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    Message message;
    MessageService ms = new MessageService();

    @Before
    public void setUp() throws Exception {
        message = ENTITY_FACTORY.createMessage();
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
        Message newMessage = ENTITY_FACTORY.createMessage();
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