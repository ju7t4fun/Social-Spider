package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.EntitySynchronizedCacheWrapperUtil;
import com.epam.lab.spider.model.entity.Post;
import com.epam.lab.spider.persistence.dao.PostDAO;
import com.epam.lab.spider.persistence.dao.savable.SavableCRUDUtil;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandra Lobanok
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
            "0 ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_GET_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM post WHERE user_id=? AND deleted = 0";

    private static final String SQL_GET_BY_USER_ID_LIMIT_WITH_SEARCH_QUERY = "SELECT * FROM post WHERE user_id = ? " +
            " AND message LIKE ?  AND deleted = 0 LIMIT ?, ?";
    private static final String SQL_GET_COUNT_BY_USER_ID_WITH_SEARCH = "SELECT COUNT(*) FROM post WHERE user_id=? AND" +
            " message LIKE ? AND deleted = 0";
    private static final String SQL_GET_BY_CATEGORY_ID_QUERY = "SELECT post.* FROM  post JOIN category_has_post ON " +
            "post.id = category_has_post.post_id WHERE category_id = ? ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_GET_BY_CATEGORY_FROM_USER_ID_QUERY = "SELECT DISTINCT category_has_post.post_id " +
            "FROM category JOIN category_has_post ON category.id = category_has_post.category_id WHERE category_id IN" +
            " (SELECT category.id FROM category JOIN user_has_category ON category.id = user_has_category.category_id" +
            " WHERE user_id = ?) ORDER BY post_id DESC LIMIT ?, ?";

    @Override
    public boolean insert(Connection connection, Post post) throws SQLException {
        Integer newId = insertQuery(connection, SQL_INSERT_QUERY,
                post.getMessage(),
                post.getDeleted(),
                post.getUserId());
        setId(post, newId);
        return newId != null;
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
            post = ENTITY_FACTORY.createPost();
            setId(post, rs.getInt("id"));
            post.setMessage(rs.getString("message"));
            post.setDeleted(rs.getBoolean("deleted"));
            post.setUserId(rs.getInt("user_id"));
            posts.add(EntitySynchronizedCacheWrapperUtil.wrap(post));
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

    @Override
    public List<Integer> getByCategoryFromUser(Connection connection, int userId, int offset, int limit) throws
            SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_BY_CATEGORY_FROM_USER_ID_QUERY, userId, offset, limit);
        List<Integer> id = new ArrayList<>();
        while (rs.next()) {
            id.add(rs.getInt("post_id"));
        }
        return id;
    }

}
