package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.entity.AbstractEntityFactory;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.impl.BasicEntityFactory;
import com.epam.lab.spider.persistence.service.PostService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * @author Oleksandra Lobanok
 */
public class PostServiceTest {
    public static final AbstractEntityFactory ENTITY_FACTORY = BasicEntityFactory.getSynchronized();

    Post post;
    PostService ps = new PostService();

    @Before
    public void setUp() throws Exception {
        post = ENTITY_FACTORY.createPost();
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
        Post newPost = ENTITY_FACTORY.createPost();
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