package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.FilterDAO;
import com.epam.lab.spider.model.entity.Filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class FilterDAOImp extends BaseDAO implements FilterDAO {
    private static final String SQL_INSERT_QUERY = "INSERT INTO filter (likes, reposts, min_time, max_time) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE filter SET likes = ?, reposts = ?, min_time = ?, max_time = ? " +
            "WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE * FROM filter WHERE id = ?";

    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM filter";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM filter WHERE id = ?";


    @Override
    public boolean insert(Connection connection, Filter filter) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY,
                filter.getLikes(),
                filter.getReposts(),
                filter.getMin_time(),
                filter.getMax_time());
    }

    @Override
    public boolean update(Connection connection, int id, Filter filter) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY,
                filter.getLikes(),
                filter.getReposts(),
                filter.getMin_time(),
                filter.getMax_time(),
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
            filter.setReposts(rs.getInt("reports"));
            filter.setMin_time(rs.getLong("min_time"));
            filter.setMax_time(rs.getLong("max_time"));
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
