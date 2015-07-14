package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.TaskDAO;
import com.epam.lab.spider.model.db.dao.savable.SavableCRUDUtil;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class TaskDAOImp extends BaseDAO implements TaskDAO {

    private static final String SQL_INSERT_QUERY_NEW = "INSERT INTO task (user_id, filter_id, type, state, deleted, " +     // #1
            "signature, hashtags, content_type, actions_after_posting, start_time_type, work_time_limit, next_task_run," +  // #2
            "interval_min, interval_max,  grabbing_size, post_count, post_delay_min, post_delay_max, grabbing_mode, " +     // #3
            "grabbing_type, repeat_type, repeat_count  ) "+
            //set added field at #1 count 5
            "VALUES (?, ?, ?, ?, ?, " +
            //set added field at #2 count 7
            "?, ?, ?, ?, ?, ?, ?, " +
            //set added field at #3 count 7
            "?, ?, ?, ?, ?, ?, ?, "+
            //set added field at #4 count 3
            "?, ?, ? ) ";
    private static final String SQL_UPDATE_QUERY = "UPDATE task SET user_id = ?, filter_id = ?, type = ? " +
            "state = ?, deleted = ? WHERE id = ?";

    private static final String SQL_UPDATE_QUERY_NEW = "UPDATE task SET " +
            "user_id = ?, filter_id = ?, type = ?, state = ?, deleted = ?, " +                                  // #1[5]
            "signature = ?, hashtags = ?, content_type = ?, actions_after_posting = ?, start_time_type = ?, " + // #2[5]
            "work_time_limit = ?, next_task_run = ?, interval_min = ?, interval_max = ?, grabbing_size = ?, " + // #3[5]
            "post_count = ?, post_delay_min = ?, post_delay_max = ?, grabbing_mode = ?, " +                      // #4[4]
            "grabbing_type = ?, repeat_type = ?, repeat_count = ? " +                                               // #5[3]
            "WHERE id = ?";                                                                                     // ID
    // quick update query
    private static final String SQL_UPDATE_NEXT_TIME_RUN_QUERY = "UPDATE task SET next_task_run = ? WHERE id = ?";
    private static final String SQL_UPDATE_STATE_QUERY = "UPDATE task SET state = ? WHERE id = ?";
    private static final String SQL_UPDATE_STATE_AND_NEXT_TIME_RUN_QUERY = "UPDATE task SET state = ?, next_task_run = ? WHERE id = ?";

    // private static final String SQL_DELETE_QUERY = "DELETE * FROM task WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE task SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM task WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM task WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM task WHERE user_id = ? AND deleted = false";
    private static final String SQL_GET_BY_CATEGORY_ID_QUERY = "SELECT * FROM task JOIN category_has_task ON task.id " +
            "= category_has_task.task_id WHERE category_id = ? AND deleted = false";
    private static final String SQL_GET_BY_ID_LIMIT_BY_USER_ID_QUERY =
            "SELECT * FROM task WHERE id = ? AND user_id = ?  AND deleted = false";
    private static final String SQL_GET_BY_ID_ADMIN_QUERY =
            "SELECT * FROM task WHERE id = ?  AND deleted = false";
    private static final String SQL_GET_ALL_BY_DATE_TIME_LIMIT_BY_STATE_QUERY =
            "SELECT * FROM task WHERE next_task_run < ? AND state = ?  AND deleted = false";

    private static final String SQL_GET_ALL_LIMITED_QUERY = "SELECT * FROM task WHERE deleted = false AND user_id=? LIMIT ?, ?";
    private static final String SQL_GET_COUNT = "SELECT COUNT(*) FROM task WHERE  deleted = 0 AND user_id=?";
    private static final String SQL_GET_ALL_ACTIVE_LIMITED_QUERY = "SELECT * FROM task WHERE deleted = false AND user_id=? AND state='RUNNING' LIMIT ?, ?";
    private static final String SQL_GET_ACTIVE_COUNT = "SELECT COUNT(*) FROM task WHERE  deleted = 0 AND user_id=? AND state='RUNNING'";

    private static final String SQL_GET_ALL_LIMITED_ADMIN_QUERY = "SELECT * FROM task WHERE deleted = false AND type='FAVORITE' LIMIT ?, ?";
    private static final String SQL_GET_COUNT_ADMIN = "SELECT COUNT(*) FROM task WHERE  deleted = 0 AND type='FAVORITE' ";


    @Override
    public boolean save(Connection conn, Task entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        return SavableCRUDUtil.save(conn,entity,this);
    }
    @Override
    public boolean insert(Connection connection, Task task) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY_NEW,
                //set field at #1
                task.getUserId(),
                task.getFilterId(),
                task.getType().toString().toUpperCase(),
                task.getState().toString().toUpperCase(),
                task.getDeleted(),
                //set field at #2
                task.getSignature(),
                task.getHashTags(),
                task.getContentType().getType(),
                task.getActionAfterPosting().toString().toUpperCase(),
                task.getStartTimeType().toString().toUpperCase(),
                task.getWorkTimeLimit().toString().toUpperCase(),
                task.getNextTaskRunDate(),
                //set field at #3
                task.getIntervalMin(),
                task.getIntervalMax(),
                task.getGrabbingSize(),
                task.getPostCount(),
                task.getPostDelayMin(),
                task.getPostDelayMax(),
                task.getGrabbingMode().toString().toUpperCase(),
                //set field at #4
                task.getGrabbingType().toString().toUpperCase(),
                task.getRepeat().toString().toUpperCase(),
                task.getRepeatCount()
        );
        task.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int i, Task task) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY_NEW,
                //set field at #1
                task.getUserId(),
                task.getFilterId(),
                task.getType().toString().toUpperCase(),
                task.getState().toString().toUpperCase(),
                task.getDeleted(),
                //set field at #2
                task.getSignature(),
                task.getHashTags(),
                task.getContentType().getType(),
                task.getActionAfterPosting().toString().toUpperCase(),
                task.getStartTimeType().toString().toUpperCase(),
                //set field at #3
                task.getWorkTimeLimit().toString().toUpperCase(),
                task.getNextTaskRunDate(),
                task.getIntervalMin(),
                task.getIntervalMax(),
                task.getGrabbingSize(),
                //set field at #4
                task.getPostCount(),
                task.getPostDelayMin(),
                task.getPostDelayMax(),
                task.getGrabbingMode().toString().toUpperCase(),
                //set field at #5
                task.getGrabbingType().toString().toUpperCase(),
                task.getRepeat().toString().toUpperCase(),
                task.getRepeatCount(),
                // ID
                i
        );
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Task> select(Connection connection, String query, Object... args) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Task task;
        while (rs.next()) {
            task = new Task();
            task.setId(rs.getInt("id"));
            task.setUserId(rs.getInt("user_id"));
            task.setFilterId(rs.getInt("filter_id"));
            task.setType(Task.Type.valueOf(rs.getString("type").toUpperCase()));
            task.setState(Task.State.valueOf(rs.getString("state").toUpperCase()));
            task.setDeleted(rs.getBoolean("deleted"));

            task.setSignature(rs.getString("signature"));
            task.setHashTags(rs.getString("hashtags"));
            task.setContentType(new Task.ContentType(rs.getInt("content_type")));
            task.setActionAfterPosting(Task.ActionAfterPosting.valueOf(rs.getString("actions_after_posting")));
            task.setStartTimeType(Task.StartTimeType.valueOf(rs.getString("start_time_type")));
            task.setWorkTimeLimit(Task.WorkTimeLimit.valueOf(rs.getString("work_time_limit")));
            task.setNextTaskRunDate(rs.getTimestamp("next_task_run"));
            //set field at #3
            task.setIntervalMin( rs.getInt("interval_min"));
            task.setIntervalMax( rs.getInt("interval_max"));
            task.setGrabbingSize(rs.getInt("grabbing_size"));
            task.setPostCount(   rs.getInt("post_count"));
            task.setPostDelayMin(rs.getInt("post_delay_min"));
            task.setPostDelayMax(rs.getInt("post_delay_max"));
            task.setGrabbingMode( Task.GrabbingMode.valueOf(rs.getString("grabbing_mode")));
            // added in rev3
            task.setGrabbingType(Task.GrabbingType.valueOf(rs.getString("grabbing_type")));
            task.setRepeat(Task.Repeat.valueOf(rs.getString("repeat_type")));
            task.setRepeatCount(rs.getInt("repeat_count"));

            taskList.add(task);
        }
        return taskList;
    }

    @Override
    public List<Task> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Task getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

    @Override
    public List<Task> getByUserId(Connection connection, int id) throws SQLException {
        return select(connection, SQL_GET_BY_USER_ID_QUERY, id);
    }

    @Override
    public List<Task> getByCategoryId(Connection connection, int id) throws SQLException {
        return select(connection, SQL_GET_BY_CATEGORY_ID_QUERY, id);
    }

    @Override
    public Task getByIdLimitByUserId(Connection connection, int id, int userId) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_LIMIT_BY_USER_ID_QUERY, id, userId));
    }

    @Override
    public Task getByIdAdminRules(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_ADMIN_QUERY, id));
    }

    @Override
    public List<Task> getAllByNextRunDateLimitByState(Connection connection, Date date, Task.State state) throws SQLException {
        return select(connection, SQL_GET_ALL_BY_DATE_TIME_LIMIT_BY_STATE_QUERY, date,state.toString());
    }
    @Override
    public boolean updateNextTimeRun(Connection connection, int id, Date date) throws SQLException{
        return changeQuery(connection, SQL_UPDATE_NEXT_TIME_RUN_QUERY, date, id);
    }
    @Override
    public boolean updateState(Connection connection, int id, Task.State state) throws SQLException{
        return changeQuery(connection, SQL_UPDATE_STATE_QUERY, state, id);
    }
    @Override
    public boolean updateNextTimeRunAndState(Connection connection, int id, Date date,Task.State state) throws SQLException{
        return changeQuery(connection, SQL_UPDATE_STATE_AND_NEXT_TIME_RUN_QUERY,  state, date, id);

    }

    @Override
    public List<Task> getAllLimited(Connection connection, int userId, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_LIMITED_QUERY, userId, start, ammount);
    }
    @Override
    public int getCount(Connection connection, int userId) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT, userId);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public List<Task> getAllActiveLimited(Connection connection, int userId, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_ACTIVE_LIMITED_QUERY, userId, start, ammount);
    }

    @Override
    public int getActiveCount(Connection connection, int userId) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_ACTIVE_COUNT, userId);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }

    @Override
    public List<Task> getAllLimitedAdmin(Connection connection, int start, int ammount) throws SQLException {
        return select(connection, SQL_GET_ALL_LIMITED_ADMIN_QUERY, start, ammount);
    }
    @Override
    public int getCountAdmin(Connection connection) throws SQLException {
        ResultSet rs = selectQuery(connection, SQL_GET_COUNT_ADMIN);
        if (rs.next()) {
            return rs.getInt("COUNT(*)");
        }
        return 0;
    }
}
