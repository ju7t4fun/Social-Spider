package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sasha on 13.07.2015.
 */
public class TaskServiceTest {

    Task task;
    TaskService ts = new TaskService();

    @Before
    public void setUp() throws Exception {
        task = new Task();
        task.setUserId(1);
        task.setFilterId(3);
        task.setType(Task.Type.FAVORITE);
        task.setState(Task.State.RUNNING);
        task.setDeleted(false);
        task.setHashTags("#TEST #hashtags");
        task.setSignature("testSignature");
        task.setNextTaskRunDate(new Date());
    }

    @Test
    public void testSave() throws Exception {
        Boolean check = ts.save(task);
        assertTrue(check);
    }
/*
    @Test
    public void testInsert() throws Exception {
        Boolean check = ts.insert(task);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        Task newTask = new Task();
        newTask.setUserId(1);
        newTask.setFilterId(4);
        newTask.setType(Task.Type.FAVORITE);
        newTask.setState(Task.State.STOPPED);
        newTask.setDeleted(false);
        newTask.setHashTags("#NEW #hashtags");
        newTask.setSignature("NEWSignature");
        newTask.setNextTaskRunDate(new Date());
        Boolean check = ts.update(3, newTask);
        assertTrue(check);
    }
*/
    @Test
    public void testDelete() throws Exception {
        ts.delete(5);
        Task newTask = ts.getById(5);
        assertTrue(newTask.getDeleted());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Task> tasks = ts.getAll();
        assertTrue(tasks.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Task newTask = ts.getById(3);
        assertNotNull(newTask);
    }

    @Test
    public void testGetByUserId() throws Exception {
        List<Task> tasks = ts.getByUserId(1);
        assertTrue(tasks.size() > 0);
    }

    @Test
    public void testGetByIdAndLimitByUserId() throws Exception {
        Task newTask = ts.getByIdAndLimitByUserId(3, 1);
        assertNotNull(newTask);
    }

    @Test
    public void testGetByIdNoLimit() throws Exception {
        Task newTask = ts.getByIdNoLimit(3);
        assertNotNull(newTask);
    }

    @Test
    public void testGetByCategoryId() throws Exception {
        List<Task> tasks = ts.getByCategoryId(2);
        assertTrue(tasks.size() > 0);
    }

    @Test
    public void testGetCount() throws Exception {
        Integer count = ts.getCount(1);
        assertNotNull(count);
    }

    @Test
    public void testGetActiveCount() throws Exception {
        Integer count = ts.getActiveCount(1);
        assertNotNull(count);
    }

    @Test
    public void testGetCountAdmin() throws Exception {
        Integer count = ts.getCountAdmin();
        assertNotNull(count);
    }
}