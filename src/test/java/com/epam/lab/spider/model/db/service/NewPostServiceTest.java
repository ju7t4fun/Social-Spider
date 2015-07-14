package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.NewPost;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Sasha on 12.07.2015.
 */
public class NewPostServiceTest {

    NewPost post;
    NewPostService nps = new NewPostService();

    @Before
    public void setUp() throws Exception {
        post = new NewPost();
        post.setPostId(100);
        post.setWallId(1);
        post.setUserId(1);
        post.setState(NewPost.State.POSTED);
        post.setDeleted(false);
        post.setPostTime(new Date());
    }


    @Test
    public void testSave() throws Exception {
        Boolean check = nps.save(post);
        assertTrue(check);
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = nps.insert(post);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        nps.delete(3);
        NewPost newPost = nps.getById(3);
        assertNull(newPost);
    }

    @Test
    public void testGetAll() throws Exception {
        List<NewPost> posts = nps.getAll();
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        NewPost newPost = nps.getById(82);
        assertNotNull(newPost);
    }
/*
    @Test
    public void testSetSpecialStageByOwner() throws Exception {
        Boolean check = nps.setSpecialStageByOwner(1, NewPost.State.DELETED);
        assertTrue(check);
    }

    @Test
    public void testSetSpecialStageByWall() throws Exception {
        Boolean check = nps.setSpecialStageByWall(12, NewPost.State.DELETED);
        assertTrue(check);
    }

    @Test
    public void testSetSpecialStageByProfile() throws Exception {
        Boolean check = nps.setSpecialStageByProfile(1, NewPost.State.DELETED);
        assertTrue(check);
    }
*/
    @Test
    public void testGetByUserId() throws Exception {
        List<NewPost> posts = nps.getByUserId(1);
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