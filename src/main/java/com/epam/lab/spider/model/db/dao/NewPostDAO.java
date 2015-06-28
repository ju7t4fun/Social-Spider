package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public interface NewPostDAO extends CRUD<NewPost>,SavableDAO<NewPost> {

    List<NewPost> getAllUnpostedByDate(Connection connection, Date date) throws SQLException;

    public boolean setErrorStateByWall(Connection connection, Integer wallId) throws SQLException ;
    public boolean setRestoredStateByWall(Connection connection, Integer wallId) throws SQLException ;

    public boolean setErrorStateByOwner(Connection connection, Integer ownerId) throws SQLException ;
//    public boolean setRestoredStateByOwner(Connection connection, Integer ownerId) throws SQLException ;

    public boolean setErrorStateByProfile(Connection connection, Integer profileId) throws SQLException ;
//    public boolean setRestoredStateByProfile(Connection connection, Integer profileId) throws SQLException ;

    String getMessageById(Connection connection, int id) throws SQLException;

}
