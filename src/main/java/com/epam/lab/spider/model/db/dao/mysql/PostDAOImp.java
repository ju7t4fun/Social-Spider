package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.PostDAO;
import com.epam.lab.spider.model.db.dao.savable.SavableCRUDUtil;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class PostDAOImp extends BaseDAO implements PostDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO post (message, deleted, user_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE post SET message = ?, deleted = ?, user_id = ? WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE FROM post WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE post SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM post WHERE deleted = false";
    private static final String SQL_GET_ALL_NOT_IN_NEWPOST_QUERY = "SELECT * FROM post WHERE deleted = false AND id " +
            "NOT IN (SELECT post_id AS id FROM new_post) ";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM post WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM post WHERE user_id = ? AND deleted = 0";
    private static final String SQL_GET_BY_USER_ID_LIMIT_QUERY = "SELECT * FROM post WHERE user_id = ? AND deleted = " +
            "0 LIMIT ?, ?";
    private static final String SQL_GET_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM post WHERE user_id=? AND deleted = 0";

    private static final String SQL_GET_BY_USER_ID_LIMIT_WITH_SEARCH_QUERY = "SELECT * FROM post WHERE user_id = ? " +
            " AND message LIKE ?  AND deleted = 0 LIMIT ?, ?";
    private static final String SQL_GET_COUNT_BY_USER_ID_WITH_SEARCH = "SELECT COUNT(*) FROM post WHERE user_id=? AND" +
            " message LIKE ? AND deleted = 0";
    private static final String SQL_GET_BY_CATEGORY_ID_QUERY = "SELECT post.* FROM  post JOIN category_has_post ON " +
            "post.id = category_has_post.post_id WHERE category_id = ? ORDER BY id DESC LIMIT ?, ?";

    @Override
    public boolean insert(Connection connection, Post post) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                post.getMessage(),
                post.getDeleted(),
                post.getUserId());
        post.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Post post) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                post.getMessage(),
                post.getDeleted(),
                post.getUserId(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Post> select(Connection connection, String query, Object... args) throws SQLException {
        List<Post> posts = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Post post;
        while (rs.next()) {
            post = new Post();
            post.setId(rs.getInt("id"));
            post.setMessage(rs.getString("message"));
            post.setDeleted(rs.getBoolean("deleted"));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<Post> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public List<Post> getAllNotInNewPost(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_NOT_IN_NEWPOST_QUERY);
    }

    @Override
    public Post getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public boolean save(Connection conn, Post entity) throws UnsupportedDAOException, ResolvableDAOException,
            InvalidEntityException {
        return SavableCRUDUtil.saveFromInterface(conn, entity);
    }

    @Override
    public List<Post> getByUserId(Connection connection, Integer userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

    @Override
    public List<Post> getByUserId(Connection connection, Integer id, int page, int size) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_LIMIT_QUERY, id, page, size);
    }

    @Override
    public int getCountByUserId(Connection connection, Integer id) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_BY_USER_ID, id);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public List<Post> getByUserIdWithSearch(Connection connection, Integer id, int page, int size, String
            messageToSearch) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_LIMIT_WITH_SEARCH_QUERY, id, messageToSearch, page, size);
    }

    @Override
    public int getCountByUserIdWithSearch(Connection connection, Integer id, String messageToSearch) throws
            SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_BY_USER_ID_WITH_SEARCH, id, messageToSearch);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public List<Post> getByCategoryId(Connection connection, int categoryId, int offset, int limit) throws
            SQLException {
        return select(connection, SQL_GET_BY_CATEGORY_ID_QUERY, categoryId, offset, limit);
    }

}
