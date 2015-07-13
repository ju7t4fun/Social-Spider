package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.Wall;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Sasha on 12.07.2015.
 */
public class WallServiceTest {

    Wall wall;
    WallService ws = new WallService();

    @Before
    public void setUp() throws Exception {
        wall = new Wall();
        wall.setOwner_id(1);
        wall.setProfile_id(1);
        wall.setPermission(Wall.Permission.READ);
        wall.setDeleted(false);
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = ws.insert(wall);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        Wall newWall = new Wall();
        newWall.setOwner_id(1);
        newWall.setProfile_id(1);
        newWall.setPermission(Wall.Permission.WRITE);
        newWall.setDeleted(true);
        Boolean check = ws.update(1, newWall);
        assertTrue(check);
    }

    @Test
    public void testGetAllByOwnerIdAndPermission() throws Exception {
        List<Wall> walls = ws.getAllByOwnerIdAndPermission(1, Wall.Permission.READ);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testDelete() throws Exception {
        ws.delete(3);
        Wall deletedWall = ws.getById(3);
        assertNull(deletedWall);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Wall> walls = ws.getAll();
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Wall newWall = ws.getById(1);
        assertNotNull(newWall);
    }

    @Test
    public void testGetAllByProfileID() throws Exception {
        List<Wall> walls = ws.getAllByProfileID(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetAllByUserID() throws Exception {
        List<Wall> walls = ws.getAllByUserID(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetReadByUserId() throws Exception {
        List<Wall> walls = ws.getReadByUserId(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetWriteByUserId() throws Exception {
        List<Wall> walls = ws.getWriteByUserId(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetByOwnerId() throws Exception {
        List<Wall> walls = ws.getByOwnerId(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetReadByOwnerId() throws Exception {
        List<Wall> walls = ws.getReadByOwnerId(1);
        assertTrue(walls.size() > 0);
    }

    @Test
    public void testGetWriteByOwnerId() throws Exception {
        List<Wall> walls = ws.getWriteByOwnerId(1);
        assertTrue(walls.size() > 0);
    }
}