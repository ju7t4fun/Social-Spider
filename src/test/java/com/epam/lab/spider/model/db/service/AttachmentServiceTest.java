package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.AttachmentService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleksandra Lobanok
 */
public class AttachmentServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getInstance();

    Attachment attachment;
    AttachmentService as = new AttachmentService();

    @Before
    public void setUp() throws Exception {
        attachment = ENTITY_FACTORY.createAttachment();
        attachment.setPayload("testurl");
        attachment.setPostId(10);
        attachment.setType(Attachment.Type.DOC);
    }

    @Test
    public void testSave() throws Exception {
        assertNotNull(as.save(attachment));
    }

    @Test
    public void testGetAll() throws Exception {
        List<Attachment> attachments = as.getAll();
        assertTrue(attachments.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Attachment newAttachment = as.getById(100);
        assertNotNull(newAttachment);
    }

    @Test
    public void testGetByPostId() throws Exception {
        List<Attachment> attachments = as.getByPostId(75);
        assertTrue(attachments.size() > 0);
    }
}