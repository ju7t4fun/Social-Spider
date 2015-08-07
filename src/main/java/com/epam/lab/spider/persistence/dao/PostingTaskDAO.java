package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.PostingTask;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksandra Lobanok
 * @author Yura Kovalik
 */
public interface PostingTaskDAO extends CRUD<PostingTask>, com.epam.lab.spider.persistence.dao.savable.SavableDAO<PostingTask> {

    List<PostingTask> getAllUnpostedByDate(Connection connection, Date date) throws SQLException;

    List<PostingTask> getAllUndeletedByDate(Connection connection, Date date) throws SQLException;

    boolean setErrorStateByWall(Connection connection, Integer wallId) throws SQLException;

    boolean setRestoredStateByWall(Connection connection, Integer wallId) throws SQLException;

    boolean setErrorStateByOwner(Connection connection, Integer ownerId) throws SQLException;

    boolean setErrorStateByProfile(Connection connection, Integer profileId) throws SQLException;

    List<PostingTask> getByUserId(Connection connection, int userId) throws SQLException;

    String getMessageById(Connection connection, int id) throws SQLException;

    List<PostingTask> getAllWithQuery(Connection connection, String someQuery) throws SQLException;

    int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException;

    boolean updateState(Connection connection, int id, PostingTaskImpl.State state) throws SQLException;

    int getPostedCountByUserIdWithSearch(Connection connection, Integer id, String sSearch) throws SQLException;

    List<PostingTask> getPostedByUserIdWithSearch(Connection connection, Integer id, int page, int size, String sSearch)
            throws SQLException;

    List<PostingTask> getPostedByUserId(Connection connection, Integer id, int page, int size) throws SQLException;

    int getPostedCountByUserId(Connection connection, Integer id) throws SQLException;

    List<PostingTask> getByUserIdWithParameters(Connection connection, Integer id, int offset, int limit, String type,
                                            String q, String order, Integer wallId) throws SQLException;

    int getCountAllByUserIdWithParameters(Connection connection, Integer id, String type, String q, Integer wallId)
            throws SQLException;

    Map<Long, Integer> statisticsPosting(Connection connection, String date) throws SQLException;

    boolean deleteByWallId(Connection connection, int wallId) throws SQLException;

    boolean deleteByPostId(Connection connection, int postDd) throws SQLException;

    List<PostingTask> getByUserIdWithLimits(Connection connection, Integer userId, int offset, int limit) throws SQLException;
}
