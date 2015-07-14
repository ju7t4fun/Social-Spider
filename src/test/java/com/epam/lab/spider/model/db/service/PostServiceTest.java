package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.Post;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Sasha on 12.07.2015.
 */
public class PostServiceTest {

    Post post;
    PostService ps = new PostService();

    @Before
    public void setUp() throws Exception {
        post = new Post();
        post.setMessage("TestMessage");
        post.setDeleted(false);
        post.setUserId(1);
    }

    @Test
    public void testSave() throws Exception {
        Boolean check = ps.save(post);
        assertTrue(check);
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = ps.insert(post);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        Post newPost = new Post();
        newPost.setMessage("NewTestMessage");
        newPost.setDeleted(false);
        newPost.setUserId(1);
        Boolean check = ps.update(1, newPost);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        ps.delete(2);
        Post deletedPost = ps.getById(2);
        assertNull(deletedPost);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Post> posts = ps.getAll();
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Post newPost = ps.getById(1);
        assertNotNull(newPost);
    }

    @Test
    public void testGetByUserId() throws Exception {
        List<Post> posts = ps.getByUserId(1);
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetAllNotInNewPost() throws Exception {
        List<Post> posts = ps.getAllNotInNewPost();
        assertTrue(posts.size() > 0);
    }

    @Test
    public void testGetCountByUserId() throws Exception {
        Integer count = ps.getCountByUserId(1);
        assertNotNull(count);
    }
}