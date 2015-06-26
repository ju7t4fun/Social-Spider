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
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class TaskDAOImp extends BaseDAO implements TaskDAO {

    private static final String SQL_INSERT_QUERY_NEW = "INSERT INTO task (user_id, filter_id, type, state, deleted, " +     // #1
            "signature, hashtags, content_type, actions_after_posting, start_time_type, work_time_limit, next_task_run," +  // #2
            "interval_min, interval_max,  grabbing_size, post_count, post_delay_min, post_delay_max, grabbing_mode) " +     // #3
            //set added field at #1 count 5
            "VALUES (?, ?, ?, ?, ?, " +
            //set added field at #2 count 7
            "?, ?, ?, ?, ?, ?, ?, " +
            //set added field at #3 count 7
            "?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE task SET user_id = ?, filter_id = ?, type = ? " +
            "state = ?, deleted = ? WHERE id = ?";

    private static final String SQL_UPDATE_QUERY_NEW = "UPDATE task SET " +
            "user_id = ?, filter_id = ?, type = ?, state = ?, deleted = ?, " +                                  // #1[5]
            "signature = ?, hashtags = ?, content_type = ?, actions_after_posting = ?, start_time_type = ?, " + // #2[5]
            "work_time_limit = ?, next_task_run = ?, interval_min = ?, interval_max = ?, grabbing_size = ?, " + // #3[5]
            "post_count = ?, post_delay_min = ?, post_delay_max = ?, grabbing_mode = ? " +                      // #4[4]
            "WHERE id = ?";                                                                                     // ID
    // private static final String SQL_DELETE_QUERY = "DELETE * FROM task WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE task SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM task WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM task WHERE id = ? AND deleted = false";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM task WHERE user_id = ? AND deleted = false";
    private static final String SQL_GET_BY_CATEGORY_ID_QUERY = "SELECT * FROM task JOIN category_has_task ON task.id " +
            "= category_has_task.task_id WHERE category_id = ? AND deleted = false";

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
                task.getGrabbingMode().toString().toUpperCase()
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
    public boolean save(Connection conn, Task entity) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        return SavableCRUDUtil.save(conn,entity,this);
    }
}
