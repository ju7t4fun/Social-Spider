package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.NewPostDAO;
import com.epam.lab.spider.model.db.dao.savable.SavableCRUDUtil;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.NewPost;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sasha on 12.06.2015.
 * Updated by shell on 15.06.2015.
 */
public class NewPostDAOImp extends BaseDAO implements NewPostDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO new_post (post_id, wall_id, post_time, delete_time, " +
            "state, deleted, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE new_post SET post_id = ?, wall_id = ?, post_time = ?," +
            "delete_time = ?, state = ?, deleted = ?, user_id = ?, vk_post_id = ? WHERE id = ?";
    // private static final String SQL_DELETE_QUERY = "DELETE FROM new_post WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE new_post SET deleted = true WHERE id = ?";
    private static final String SQL_DELETE_BY_WALLID_QUERY = "UPDATE new_post SET deleted = true WHERE wall_id = ?";
    private static final String SQL_DELETE_BY_POSTID_QUERY = "UPDATE new_post SET deleted = true WHERE post_id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM new_post WHERE deleted = 0";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM new_post WHERE id = ? AND deleted = 0";
    private static final String SQL_SELECT_CREATED_BY_DATE_LE = "SELECT * FROM new_post WHERE state in " +
            "('CREATED', 'RESTORED') AND post_time < ? AND deleted = 0";
    private static final String SQL_SELECT_UNDELETED_BY_DELETE_DATE_LE = "SELECT * FROM new_post WHERE state LIKE " +
            "'POSTED' AND delete_time < ?  AND deleted = 0";

    private static final String SQL_SET_ERROR_STATE_BASE = "UPDATE new_post SET state = 'ERROR' WHERE state IN " +
            "('CREATED', 'POSTING','RESTORED') AND wall_id IN ";
    private static final String SQL_SET_RESTORED_STATE_BASE = "UPDATE new_post SET state = 'RESTORED' WHERE state LIKE " +
            "'ERROR' AND wall_id IN ";

    private static final String SQL_WALL_PREDICATE = " ? ";
    private static final String SQL_OWNER_PREDICATE = " SELECT id FROM wall WHERE owner_id = ? ";
    private static final String SQL_PROFILE_PREDICATE = " SELECT id FROM wall WHERE profile_id = ? ";
    private static final String SQL_SET_ERROR_STAGE_BY_WALL_ID = SQL_SET_ERROR_STATE_BASE + "(" + SQL_WALL_PREDICATE
            + ")";
    private static final String SQL_SET_RESTORED_STAGE_BY_WALL_ID = SQL_SET_RESTORED_STATE_BASE + "(" +
            SQL_WALL_PREDICATE + ")";
    private static final String SQL_SET_ERROR_STAGE_BY_OWNER_ID = SQL_SET_ERROR_STATE_BASE + "(" +
            SQL_OWNER_PREDICATE + ")";
    private static final String SQL_SET_ERROR_STAGE_BY_PROFILE_ID = SQL_SET_ERROR_STATE_BASE + "(" +
            SQL_PROFILE_PREDICATE + ")";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM new_post WHERE user_id = ? AND deleted = 0";
    private static final String SQL_GET_MESSAGE_BY_newPostQUERY = "SELECT post.message AS message FROM ( new_post " +
            "JOIN post ON post.id=new_post.post_id  AND new_post.post_id=? AND new_post.deleted = false)";
    private static final String SQL_UPDATE_STATE_QUERY = "UPDATE new_post SET state = ? WHERE id = ?";

    private static final String SQL_GET_POSTED_COUNT_BY_USER_ID_WITH_SEARCH = "SELECT COUNT(*) FROM new_post JOIN " +
            "post ON post.id = new_post.post_id WHERE new_post.user_id = ? AND post.message LIKE ? AND new_post" +
            ".deleted = 0 AND state = 'POSTED'";
    private static final String SQL_GET_POSTED_COUNT_BY_USER_ID_QUERY = "SELECT COUNT(*) FROM new_post WHERE user_id " +
            "= ? AND deleted = 0 AND state = 'POSTED'";
    private static final String SQL_GET_POSTED_BY_USER_ID_WITH_SEARCH_QUERY = "SELECT new_post.* FROM new_post JOIN " +
            "post ON post.id = new_post.post_id WHERE new_post.user_id = ? AND post.message LIKE ? AND new_post" +
            ".deleted = 0 AND state = 'POSTED' ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_GET_POSTED_BY_USER_ID_QUERY = "SELECT * FROM new_post WHERE user_id = ? AND" +
            " deleted = 0 AND state = 'POSTED' ORDER BY id DESC LIMIT ?, ?";
    private static final String SQL_BY_USER_ID_WITH_PARAMETERS_QUERY = "SELECT new_post.* FROM new_post JOIN " +
            "post ON post.id = new_post.post_id WHERE new_post.user_id = ? AND new_post.state = ? AND new_post" +
            ".deleted = 0 {SEARCH_SUBQUERY} {FILTRATION_SUBQUERY} {ORDER_SUBQUERY} LIMIT ?, ?";
    private static final String SQL_COUNT_ALL_BY_USER_ID_WITH_PARAMETERS_QUERY = "SELECT COUNT(*) FROM new_post " +
            "JOIN post ON post.id = new_post.post_id WHERE new_post.user_id = ? AND new_post.state = ? AND new_post" +
            ".deleted = 0 {SEARCH_SUBQUERY} {FILTRATION_SUBQUERY} {ORDER_SUBQUERY}";
    private static final String SQL_STATISTICS_POSTED_QUERY = "SELECT  COUNT(*) AS count, DATE_FORMAT(post_time, " +
            "'%Y-%m-%d %H') AS date FROM new_post WHERE post_time > ? AND post_time <= ? AND state = 'POSTED' GROUP " +
            "BY UNIX_TIMESTAMP(post_time) DIV 3600;";
    private static final String SQL_BY_USER_ID_WITH_LIMITS_QUERY = "SELECT * FROM new_post WHERE user_id = ? AND deleted = 0 ORDER BY id DESC LIMIT ?, ?";

    @Override
    public boolean insert(Connection connection, NewPost post) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                post.getPostId(),
                post.getWallId(),
                post.getPostTime(),
                post.getDeleteTime(),
                post.getState().toString().toUpperCase(),
                post.getDeleted(),
                post.getUserId());
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
                post.getUserId(),
                post.getVkPostId(),
                id);
    }

    @Override
    public boolean updateState(Connection connection, int id, NewPost.State state) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_STATE_QUERY,
                state.toString().toUpperCase(),
                id);
    }

    @Override
    public int getPostedCountByUserIdWithSearch(Connection connection, Integer id, String sSearch) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_POSTED_COUNT_BY_USER_ID_WITH_SEARCH, id, sSearch);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }

    @Override
    public List<NewPost> getPostedByUserIdWithSearch(Connection connection, Integer id, int page, int size, String
            sSearch) throws SQLException {
        return select(connection, SQL_GET_POSTED_BY_USER_ID_WITH_SEARCH_QUERY, id, sSearch, page, size);
    }

    @Override
    public List<NewPost> getPostedByUserId(Connection connection, Integer id, int page, int size) throws SQLException {
        return select(connection, SQL_GET_POSTED_BY_USER_ID_QUERY, id, page, size);
    }

    @Override
    public int getPostedCountByUserId(Connection connection, Integer id) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_POSTED_COUNT_BY_USER_ID_QUERY, id);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }

    @Override
    public List<NewPost> getByUserIdWithLimits(Connection connection, Integer userId, int offset, int limit) throws SQLException {
        return select(connection, SQL_BY_USER_ID_WITH_LIMITS_QUERY, userId,  offset, limit);
    }

    @Override
    public List<NewPost> getByUserIdWithParameters(Connection connection, Integer id, int offset, int limit, String
            type, String q, String order, Integer wallId) throws SQLException {
        String SEARCH_SUBQUERY = q == null ? " " : " AND post.message LIKE '%" + q + "%' ";
        String ORDER_SUBQUERY = order == null ? " ORDER BY new_post.id DESC " : "ORDER BY new_post.post_time " + order
                .toUpperCase();
        String FILTRATION_SUBQUERY = wallId == null ? " " : "AND new_post.wall_id = " + wallId + " ";
        return select(connection, SQL_BY_USER_ID_WITH_PARAMETERS_QUERY
                .replace("{SEARCH_SUBQUERY}", SEARCH_SUBQUERY)
                .replace("{FILTRATION_SUBQUERY}", FILTRATION_SUBQUERY)
                .replace("{ORDER_SUBQUERY}", ORDER_SUBQUERY), id, type, offset, limit);
    }

    @Override
    public int getCountAllByUserIdWithParameters(Connection connection, Integer id, String type, String q, Integer
            wallId) throws SQLException {
        String SEARCH_SUBQUERY = q == null ? " " : " AND post.message LIKE '%" + q + "%' ";
        String FILTRATION_SUBQUERY = wallId == null ? " " : "AND new_post.wall_id = " + wallId + " ";
        ResultSet rs = selectQuery(connection, SQL_COUNT_ALL_BY_USER_ID_WITH_PARAMETERS_QUERY
                .replace("{SEARCH_SUBQUERY}", SEARCH_SUBQUERY)
                .replace("{FILTRATION_SUBQUERY}", FILTRATION_SUBQUERY), id, type);
        if (rs.next())
            return rs.getInt("COUNT(*)");
        return -1;
    }

    @Override
    public Map<Long, Integer> statisticsPosting(Connection connection, String date) throws SQLException {
        String fromDate = date + " 00:00:00";
        String toDate = date + "23:59:59";
        ResultSet rs = selectQuery(connection, SQL_STATISTICS_POSTED_QUERY, fromDate, toDate);
        Map<Long, Integer> statistics = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (rs.next()) {
            String format = rs.getString("date") + ":00:00";
            try {
                statistics.put(dateFormat.parse(format).getTime(), rs.getInt("count"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return statistics;
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public boolean deleteByPostId(Connection connection, int postDd) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_POSTID_QUERY, postDd);
    }

    @Override
    public boolean deleteByWallId(Connection connection, int wallId) throws SQLException {
        return changeQuery(connection, SQL_DELETE_BY_WALLID_QUERY, wallId);
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
            nPost.setUserId(rs.getInt("user_id"));
            nPost.setVkPostId(rs.getInt("vk_post_id"));
            posts.add(nPost);
        }
        return posts;
    }

    @Override
    public List<NewPost> getAllWithQuery(Connection connection, String someQuery) throws SQLException {
        List<NewPost> posts = new ArrayList<>();
        ResultSet rs = selectQuery(connection, someQuery);
        NewPost nPost;
        while (rs.next()) {
            nPost = new NewPost();
            nPost.setId(rs.getInt("id"));
            nPost.setPostId(rs.getInt("post_id"));
            nPost.setWallId(rs.getInt("wall_id"));
            nPost.setPostTime(rs.getTimestamp("post_time"));
            nPost.setState(NewPost.State.valueOf(rs.getString("state")));
            nPost.setVkPostId(rs.getInt("vk_post_id"));
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

    @Override
    public String getMessageById(Connection connection, int id) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_MESSAGE_BY_newPostQUERY, id);
        while (rs.next()) {
            return rs.getString("message");
        }
        return "No Message :(";
    }

    public List<NewPost> getAllUnpostedByDate(Connection connection, Date date) throws SQLException {
        return select(connection, SQL_SELECT_CREATED_BY_DATE_LE, date);
    }

    public List<NewPost> getAllUndeletedByDate(Connection connection, Date date) throws SQLException {
        return select(connection, SQL_SELECT_UNDELETED_BY_DELETE_DATE_LE, date);
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
    public List<NewPost> getByUserId(Connection connection, int userId) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, userId);
    }

    @Override
    public boolean save(Connection conn, NewPost entity) throws UnsupportedDAOException, ResolvableDAOException,
            InvalidEntityException {
        return SavableCRUDUtil.saveFromInterface(conn, entity);
    }
//    public boolean setRestoredStateByProfile(Connection connection, Integer profileId) throws SQLException {
//        return changeQuery(connection, SQL_SET_RESTORED_STAGE_BY_PROFILE_ID, profileId);
//    }

    //should remake later
    @Override
    public int getCountWithQuery(Connection connection, String SQL_SOME_QUERY) throws SQLException {

        ResultSet rs = selectQuery(connection, SQL_SOME_QUERY);
        if (rs != null) {
            while (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        }
        return -322;
    }

}
