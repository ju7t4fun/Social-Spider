package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.NewPostDAO;
import com.epam.lab.spider.model.entity.NewPost;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.PostMetadata;

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
            "metadata_id, state_id, deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE new_post SET post_id = ?, wall_id = ?, post_time = ?," +
            "delete_time = ?, metadata_id = ?, state_id = ?, deleted = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE FROM new_post WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE new_post SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM new_post";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM new_post WHERE id = ?";

    private static final String SQL_SELECT_CREATED_BY_DATE_LE = "SELECT * FROM new_post WHERE state_id = '1' AND post_time < ?";
    @Override
    public boolean insert(Connection connection, NewPost post) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                post.getPost().getId(),
                post.getWallId(),
                post.getPostTime(),
                post.getDeleteTime(),
                post.getMetadata() == null ? null : post.getMetadata().getId(),
                post.getState().getId(),
                post.getDeleted());
        post.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, NewPost post) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, post.getPost().getId(), post.getWallId(), post.getPostTime(),
                post.getDeleteTime(), post.getMetadata().getId(), post.getState().getId(), post.getDeleted(), id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<NewPost> select(Connection connection, String query, Object... args) throws SQLException {
        List<NewPost> posts = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        NewPost post;
        while (rs.next()) {
            post = new NewPost();
            post.setId(rs.getInt("id"));
            {
                Post p = new Post();
                p.setId(rs.getInt("post_id"));
                post.setPost(p);
            }
            post.setWallId(rs.getInt("wall_id"));

            post.setPostTime(rs.getTimestamp("post_time"));
            post.setDeleteTime(rs.getDate("delete_time"));
            {
                PostMetadata metadata = new PostMetadata();
                metadata.setId(rs.getInt("metadata_id"));
                post.setMetadata(metadata);
            }
            post.setState(NewPost.State.getById(rs.getInt("state_id")));
            posts.add(post);
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
