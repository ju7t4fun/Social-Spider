package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.NewPost;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    boolean setErrorStateByProfile(Connection connection, Integer profileId) throws SQLException;

    List<NewPost> getByUserId(Connection connection, int userId) throws SQLException;

    String getMessageById(Connection connection, int id) throws SQLException;

    List<NewPost> getAllWithQuery(Connection connection, String someQuery) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    public boolean updateState(Connection connection, int id, NewPost.State state) throws SQLException;

    int getPostedCountByUserIdWithSearch(Connection connection, Integer id, String sSearch) throws SQLException;

    List<NewPost> getPostedByUserIdWithSearch(Connection connection, Integer id, int page, int size, String sSearch)
            throws SQLException;

    List<NewPost> getPostedByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getPostedCountByUserId(Connection connection, Integer id) throws SQLException;

    List<NewPost> getByUserIdWithParameters(Connection connection, Integer id, int offset, int limit, String type,
                                            String q, String order, Integer wallId) throws SQLException;

    int getCountAllByUserIdWithParameters(Connection connection, Integer id, String type, String q, Integer wallId)
            throws SQLException;

    Map<Long, Integer> statisticsPosting(Connection connection, String date) throws SQLException;

    boolean deleteByWallId(Connection connection, int wallId) throws SQLException;

    boolean deleteByPostId(Connection connection, int postDd) throws SQLException;

    public List<NewPost> getByUserIdWithLimits(Connection connection, Integer userId, int offset, int limit) throws SQLException;
}
