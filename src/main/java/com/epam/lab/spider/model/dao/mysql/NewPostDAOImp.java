package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.NewPostDAO;
import com.epam.lab.spider.model.entity.NewPost;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.model.entity.PostMetadata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class NewPostDAOImp extends BaseDAO implements NewPostDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO new_post (post_id, wall_id, post_time, delete_time, " +
            "metadate_id) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE new_post SET post_id = ?, wall_id = ?, post_time = ?," +
            "delete_time = ?, metadate_id = ? WHERE id = ?";
   // private static final String SQL_DELETE_QUERY = "DELETE FROM new_post WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE new_post SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM new_post";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM new_post WHERE id = ?";

    @Override
    public boolean insert(Connection connection, NewPost post) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, post.getPost().getId(), post.getWallId(), post.getPostTime(),
                post.getDeleteTime(), post.getMetadata().getId());
    }

    @Override
    public boolean update(Connection connection, int id, NewPost post) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, post.getPost().getId(), post.getWallId(), post.getPostTime(),
                post.getDeleteTime(), post.getMetadata().getId(), id);
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
            Post p = new Post();
            p.setId(rs.getInt("post_id"));
            post.setPost(p);
            post.setWallId(rs.getInt("wall_id"));
            post.setPostTime(rs.getDate("post_time"));
            post.setDeleteTime(rs.getDate("delete_time"));
            PostMetadata metadata = new PostMetadata();
            metadata.setId(rs.getInt("metadata_id"));
            post.setMetadata(metadata);
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
}
