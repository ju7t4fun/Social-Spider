package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.AttachmentDAO;
import com.epam.lab.spider.model.entity.Attachment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class AttachmentDAOImp extends BaseDAO implements AttachmentDAO {

    private static final String SQL_INSERT_QUERY = "INSERT INTO attachment (url, post_id, type)" +
            "VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_QUERY = "UPDATE attachment SET url = ?, post_id = ?, type = ? WHERE id = ?";
    private static final String SQL_DELETE_QUERY = "DELETE FROM attachment WHERE id = ?";
    private static final String SQL_GET_ALL_QUERY = "SELECT * FROM attachment";
    private static final String SQL_GET_BY_ID_QUERY = "SELECT * FROM attachment WHERE id = ?";

    @Override
    public boolean insert(Connection connection, Attachment attachment) throws SQLException {
        return changeQuery(connection, SQL_INSERT_QUERY, attachment.getUrl(), attachment.getPostId(),
                attachment.getType().toString());
    }

    @Override
    public boolean update(Connection connection, int id, Attachment attachment) throws SQLException {
        return changeQuery(connection, SQL_UPDATE_QUERY, attachment.getUrl(), attachment.getPostId(),
                attachment.getType(), id);
    }

    @Override
    public boolean delete(Connection connection, int id) throws SQLException {
        return changeQuery(connection, SQL_DELETE_QUERY, id);
    }

    @Override
    public List<Attachment> select(Connection connection, String query, Object... args) throws SQLException {
        List<Attachment> attachments = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        Attachment attachment;
        while (rs.next()) {
            attachment = new Attachment();
            attachment.setId(rs.getInt("id"));
            attachment.setUrl(rs.getString("url"));
            attachment.setPostId(rs.getInt("post_id"));
            attachment.setType(Attachment.Type.valueOf(rs.getString("type").toUpperCase()));
            attachments.add(attachment);
        }
        return attachments;
    }

    @Override
    public List<Attachment> getAll(Connection connection) throws SQLException {
        return select(connection, SQL_GET_ALL_QUERY);
    }

    @Override
    public Attachment getById(Connection connection, int id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID_QUERY, id));
    }
}
