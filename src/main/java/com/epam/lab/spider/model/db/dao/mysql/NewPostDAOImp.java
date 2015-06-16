package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.NewPostDAO;
import com.epam.lab.spider.model.db.entity.NewPost;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public class NewPostDAOImp extends BaseDAO implements NewPostDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO new_post (post_id, wall_id, post_time, delete_time, " +
            "state, deleted) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE new_post SET post_id = ?, wall_id = ?, post_time = ?," +
            "delete_time = ?, state = ?, deleted = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE FROM new_post WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE new_post SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM new_post WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM new_post WHERE id = ? AND deleted = false";
    private static final String SQL_SELECT_CREATED_BY_DATE_LE = "SELECT * FROM new_post WHERE state = '"+NewPost.State.CREATED+"' AND " +
            "post_time < ? AND deleted = false";

    @Override
    public boolean insert(Connection connection, NewPost post) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                post.getPost().getId(),
                post.getWallId(),
                post.getPostTime(),
                post.getDeleteTime(),
                post.getState().toString().toUpperCase(),
                post.getDeleted());
        post.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, NewPost post) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                post.getPost().getId(),
                post.getWallId(),
                post.getPostTime(),
                post.getDeleteTime(),
                post.getState().toString().toUpperCase(),
                post.getDeleted(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<NewPost> select(Connection connection, String query, Object... args) throws SQLException {
        List<NewPost> posts = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        NewPost nPost;
        while (rs.next()) {
            nPost = new NewPost();
            nPost.setId(rs.getInt("id"));
            nPost.setPostId(rs.getInt("post_id"));
            nPost.setWallId(rs.getInt("wall_id"));
            nPost.setPostTime(rs.getTimestamp("post_time"));
            nPost.setDeleteTime(rs.getDate("delete_time"));
            nPost.setState(NewPost.State.valueOf(rs.getString("state")));
            nPost.setDeleted(rs.getBoolean("deleted"));
            posts.add(nPost);
        }
        return posts;
    }

    @Override
    public List<NewPost> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public NewPost getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    public List<NewPost> getAllUnpostedByDate(Connection connection, Date date) throws SQLException {
        return select(connection, SQL_SELECT_CREATED_BY_DATE_LE, date);
    }

}
