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
public interface NewPostDAO extends CRUD<NewPost>, SavableDAO<NewPost> {

    List<NewPost> getAllUnpostedByDate(Connection connection, Date date) throws SQLException;
    List<NewPost> getAllUndeletedByDate(Connection connection, Date date) throws SQLException;

    boolean setErrorStateByWall(Connection connection, Integer wallId) throws SQLException;

    boolean setRestoredStateByWall(Connection connection, Integer wallId) throws SQLException;

    boolean setErrorStateByOwner(Connection connection, Integer ownerId) throws SQLException;
//  boolean setRestoredStateByOwner(Connection connection, Integer ownerId) throws SQLException ;

    boolean setErrorStateByProfile(Connection connection, Integer profileId) throws SQLException;

    List<NewPost> getByUserId(Connection connection, int userId) throws SQLException;
   //  boolean setRestoredStateByProfile(Connection connection, Integer profileId) throws SQLException ;

    String getMessageById(Connection connection, int id) throws SQLException;
    List<NewPost> getAllWithQuery(Connection connection, String someQuery) throws SQLException;
    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    public boolean updateState(Connection connection, int id, NewPost.State state) throws SQLException;

}
