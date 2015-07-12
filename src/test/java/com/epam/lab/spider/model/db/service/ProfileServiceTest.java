package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.Profile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Sasha on 12.07.2015.
 */
public class ProfileServiceTest {

    Profile profile;
    ProfileService ps = new ProfileService();

    @Before
    public void setUp() throws Exception {
        profile = new Profile();
        profile.setUserId(1);
        profile.setVkId(1);
        profile.setAccessToken("testAccessToken");
        profile.setName("NO_NAME");
    }

    @Test
    public void testInsert() throws Exception {
        Boolean check = ps.insert(profile);
        assertTrue(check);
    }

    @Test
    public void testUpdate() throws Exception {
        Profile newProfile = new Profile();
        newProfile.setUserId(1);
        newProfile.setVkId(2);
        newProfile.setAccessToken("NewTestAccessToken");
        newProfile.setName("NO_NAME");
        Boolean check = ps.update(2, newProfile);
        assertTrue(check);
    }

    @Test
    public void testDelete() throws Exception {
        ps.delete(3);
        Profile deletedProfile = ps.getById(3);
        assertNull(deletedProfile);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Profile> profiles = ps.getAll();
        assertTrue(profiles.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        Profile newProfile = ps.getById(1);
        assertNotNull(newProfile);
    }

    @Test
    public void testGetByVkId() throws Exception {
        Profile newProfile = ps.getByVkId(16697463);
        assertNotNull(newProfile);
    }

    @Test
    public void testGetByUserId() throws Exception {
        List<Profile> profiles = ps.getByUserId(1);
        assertTrue(profiles.size() > 0);
    }

    @Test
    public void testGetCountByUserId() throws Exception {
        Integer count = ps.getCountByUserId(1);
        assertNotNull(count);
    }
}