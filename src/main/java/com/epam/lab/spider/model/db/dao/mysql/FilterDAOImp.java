package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.FilterDAO;
import com.epam.lab.spider.model.db.entity.Filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class FilterDAOImp extends BaseDAO implements FilterDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO filter (likes, reposts, comments, min_time, max_time," +
            " deleted) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE filter SET likes = ?, reposts = ?, comments = ?, min_time " +
            "= ?, max_time = ?, deleted = ? WHERE id = ?";
    //private static final String SQL_DELETE_QUERY = "DELETE * FROM filter WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "UPDATE filter SET deleted = true WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM filter WHERE deleted = false";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM filter WHERE id = ? AND deleted = false";

    @Override
    public boolean insert(Connection connection, Filter filter) throws SQLException {
        boolean res = changeQuery(connection, SQL_INSERT_QUERY,
                filter.getLikes(),
                filter.getReposts(),
                filter.getComments(),
                filter.getMinTime(),
                filter.getMaxTime(),
                filter.getDeleted());
        filter.setId(getLastInsertId(connection));
        return res;
    }

    @Override
    public boolean update(Connection connection, int id, Filter filter) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                filter.getLikes(),
                filter.getReposts(),
                filter.getComments(),
                filter.getMinTime(),
                filter.getMaxTime(),
                filter.getDeleted(),
                id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Filter> select(Connection connection, String query, Object... args) throws SQLException {
        List<Filter> filterList = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Filter filter;
        while (rs.next()) {
            filter = new Filter();
            filter.setId(rs.getInt("id"));
            filter.setLikes(rs.getInt("likes"));
            filter.setReposts(rs.getInt("reposts"));
            filter.setComments(rs.getInt("comments"));
            filter.setMinTime(rs.getLong("min_time"));
            filter.setMaxTime(rs.getLong("max_time"));
            filter.setDeleted(rs.getBoolean("deleted"));
            filterList.add(filter);
        }
        return filterList;
    }

    @Override
    public List<Filter> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Filter getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }

}
