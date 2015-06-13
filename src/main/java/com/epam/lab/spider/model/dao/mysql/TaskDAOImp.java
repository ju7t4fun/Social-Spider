package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.TaskDAO;
import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class TaskDAOImp extends BaseDAO implements TaskDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO task (user_id, filter_id, type) " +
            "VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE task SET user_id = ?, filter_id = ?, type = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE * FROM task WHERE id = ?";

    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM task";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM task WHERE id = ?";
    private static final String SQL_GET_BY_USER_ID_QUERY = "SELECT * FROM task WHERE user_id = ?";
    private static final String SQL_GET_BY_CATEGORY_ID_QUERY = "SELECT * FROM task JOIN category_has_task ON task.id " +
            "= category_has_task.task_id WHERE category_id = ?";

    @Override
    public boolean insert(Connection connection, Task task) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY,
                task.getUserId(),
                task.getFilter(),
                task.getType());
    }

    @Override
    public boolean update(Connection connection, int i, Task task) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                task.getUserId(),
                task.getFilter(),
                task.getType(),
                i);
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
            Filter filter = new Filter();
            filter.setId(rs.getInt("filter_id"));
            task.setFilter(filter);
            task.setType(Task.Type.valueOf(rs.getString("type").toUpperCase()));
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
}
