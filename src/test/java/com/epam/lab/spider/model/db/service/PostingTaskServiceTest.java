package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.PostingTaskService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
/**
 * @author Oleksandra Lobanok
 */
public class PostingTaskServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    PostingTask postingTask;
    PostingTaskService nps = new PostingTaskService();

    @Before
    public void setUp() throws Exception {
        postingTask = ENTITY_FACTORY.createPostingTask();
        postingTask.setPostId(100);
        postingTask.setWallId(1);
        postingTask.setUserId(1);
        postingTask.setState(PostingTaskImpl.State.POSTED);
        postingTask.setDeleted(false);
        postingTask.setPostTime(new Date());
    }


    @Test
    public void testSave() throws Exception {
        Boolean check = nps.save(postingTask);
        assertTrue(check);
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = nps.insert(postingTask);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        nps.delete(3);
        PostingTask postingTask = nps.getById(3);
        assertNull(postingTask);
    }

    @Test
    public void testGetAll() throws Exception {
        List<PostingTask> posts = nps.getAll();
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        PostingTask postingTask = nps.getById(82);
        assertNotNull(postingTask);
    }
    @Test
    public void testSetSpecialStageByOwner() throws Exception {
        Boolean check = nps.setSpecialStageByOwner(1, PostingTask.State.DELETED);
        assertTrue(check);
    }

    @Test
    public void testSetSpecialStageByWall() throws Exception {
        Boolean check = nps.setSpecialStageByWall(12, PostingTask.State.DELETED);
        assertTrue(check);
    }

    @Test
    public void testSetSpecialStageByProfile() throws Exception {
        Boolean check = nps.setSpecialStageByProfile(1, PostingTask.State.DELETED);
        assertTrue(check);
    }
    @Test
    public void testGetByUserId() throws Exception {
        List<PostingTask> posts = nps.getByUserId(1);
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetMessageByID() throws Exception {
        String message = nps.getMessageByID(1);
        assertNotNull(message);
    }

    @Test
    public void testGetPostedCountByUserId() throws Exception {
        Integer count = nps.getPostedCountByUserId(1);
        assertNotNull(count);
    }
}