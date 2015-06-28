package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.NewPostDAO;
import com.epam.lab.spider.model.db.dao.savable.SavableCRUDUtil;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Owner;

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
    private static final String SQL_SELECT_CREATED_BY_DATE_LE = "SELECT * FROM new_post WHERE state in " +
            "('"+NewPost.State.CREATED+"', 'RESTORED') AND " +
            "post_time < ? AND deleted = false";


    private static final String SQL_SET_ERROR_STATE_BASE = "UPDATE new_post SET state = 'ERROR' WHERE state IN ('CREATED', 'POSTING','RESTORED') AND wall_id IN ";
    private static final String SQL_SET_RESTORED_STATE_BASE = "UPDATE new_post SET state = 'RESTORED' WHERE state IN ('ERROR') AND wall_id IN ";

    private static final String SQL_WALL_PREDICATE = " ? ";
    private static final String SQL_OWNER_PREDICATE = " SELECT id FROM wall WHERE owner_id = ? ";
    private static final String SQL_PROFILE_PREDICATE = " SELECT id FROM wall WHERE profile_id = ? ";

    private static final String SQL_SET_ERROR_STAGE_BY_WALL_ID = SQL_SET_ERROR_STATE_BASE +"("+SQL_WALL_PREDICATE+")";
    private static final String SQL_SET_RESTORED_STAGE_BY_WALL_ID = SQL_SET_RESTORED_STATE_BASE + "("+SQL_WALL_PREDICATE+")";

    private static final String SQL_SET_ERROR_STAGE_BY_OWNER_ID = SQL_SET_ERROR_STATE_BASE +"("+SQL_OWNER_PREDICATE+")";
//    private static final String SQL_SET_RESTORED_STAGE_BY_OWNER_ID = SQL_SET_RESTORED_STATE_BASE + "("+SQL_OWNER_PREDICATE+")";

    private static final String SQL_SET_ERROR_STAGE_BY_PROFILE_ID = SQL_SET_ERROR_STATE_BASE +"("+SQL_PROFILE_PREDICATE+")";
//    private static final String SQL_SET_RESTORED_STAGE_BY_PROFILE_ID = SQL_SET_RESTORED_STATE_BASE + "("+SQL_PROFILE_PREDICATE+")";

    @Override
    public boolean insert(Connection connection, NewPost post) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
  //              post.getPost().getId(),
                post.getPostId(),
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
            nPost.setDeleteTime(rs.getTimestamp("delete_time"));
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

    public boolean setErrorStateByWall(Connection connection, Integer wallId) throws SQLException {
        return changeQuery(connection, SQL_SET_ERROR_STAGE_BY_WALL_ID, wallId);
    }
    public boolean setRestoredStateByWall(Connection connection, Integer wallId) throws SQLException {
        return changeQuery(connection, SQL_SET_RESTORED_STAGE_BY_WALL_ID, wallId);
    }

    public boolean setErrorStateByOwner(Connection connection, Integer ownerId) throws SQLException {
        return changeQuery(connection, SQL_SET_ERROR_STAGE_BY_OWNER_ID, ownerId);
    }
//    public boolean setRestoredStateByOwner(Connection connection, Integer ownerId) throws SQLException {
//        return changeQuery(connection, SQL_SET_RESTORED_STAGE_BY_OWNER_ID, ownerId);
//    }

    public boolean setErrorStateByProfile(Connection connection, Integer profileId) throws SQLException {
        return changeQuery(connection, SQL_SET_ERROR_STAGE_BY_PROFILE_ID, profileId);
    }

    @Override
    public boolean save(Connection conn, NewPost entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        return SavableCRUDUtil.saveFromInterface(conn, entity);
    }
//    public boolean setRestoredStateByProfile(Connection connection, Integer profileId) throws SQLException {
//        return changeQuery(connection, SQL_SET_RESTORED_STAGE_BY_PROFILE_ID, profileId);
//    }

}
