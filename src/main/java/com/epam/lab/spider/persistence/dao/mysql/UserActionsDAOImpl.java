package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.EntitySynchronizedCacheWrapperUtil;
import com.epam.lab.spider.model.entity.UserActions;
import com.epam.lab.spider.persistence.dao.UserActionsDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Kovalik
 */
public class UserActionsDAOImpl extends BaseDAO implements UserActionsDAO<UserActions> {
    private static final String TABLE_NAME = "user_actions";
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( " +
            "id INT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, date DATE NOT NULL, "+ "task_run INT NOT NULL DEFAULT 0, "+
            "post_count INT NOT NULL DEFAULT 0, attachment_count INT NOT NULL DEFAULT 0,  attachment_traffic INT NOT NULL DEFAULT 0, "+
            "PRIMARY KEY (id), UNIQUE KEY (user_id, date), " +
            "INDEX fk_user_actions_user1_idx (user_id ASC), INDEX user_action_date_idx (date ASC),"+
            "CONSTRAINT fk_user_actions_user1 FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION  ON UPDATE NO ACTION) ENGINE = InnoDB;";
    private static final String SQL_GET_BY_ID = "SELECT * FROM "+TABLE_NAME+" WHERE id = ? and date = ?";
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM "+TABLE_NAME+" WHERE user_id = ? and date = ?";
    private static final String SQL_INSERT = "INSERT INTO "+TABLE_NAME+" (task_run, post_count, attachment_count, attachment_traffic, user_id, date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE "+TABLE_NAME+" SET task_run = ?, post_count = ?, attachment_count = ?, attachment_traffic = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM "+TABLE_NAME+" WHERE id=?";
    @Override
    public  boolean createTable(Connection conn) throws SQLException {
        return changeQuery(conn,SQL_CREATE);
    }

    @Override
    public boolean insert(Connection connection, UserActions actions, Date sqlDate) throws SQLException {

        Integer newId = insertQuery(connection, SQL_INSERT,
                actions.getTaskExecuteCount(),
                actions.getPostExecuteCount(),
                actions.getAttachmentExecuteCount(),
                actions.getAttachmentTraffic(),
                actions.getUserId(),
                sqlDate);
        setId(actions, newId);
        return newId != null;
    }

    @Override
    public boolean update(Connection connection, UserActions actions) throws SQLException {
        return changeQuery(connection, SQL_UPDATE,
                actions.getTaskExecuteCount(),
                actions.getPostExecuteCount(),
                actions.getAttachmentExecuteCount(),
                actions.getAttachmentTraffic(),
                actions.getId());
    }

    @Override
    public boolean delete(Connection connection, Integer id) throws SQLException {
        return changeQuery(connection, SQL_DELETE, id);
    }

    @Override
    public UserActions getById(Connection connection,  Integer id) throws SQLException {
        return first(select(connection, SQL_GET_BY_ID, id));
    }

    @Override
    public UserActions getByUserId(Connection connection, Integer userId, Date sqlDate) throws SQLException {
        return first(select(connection, SQL_GET_BY_USER_ID, userId, sqlDate));
    }

    public List<UserActions> select(Connection connection, String query, Object... args) throws SQLException {
        List<UserActions> userActionsList = new ArrayList<>();
        ResultSet rs = selectQuery(connection, query, args);
        UserActions userActions;
        while (rs.next()) {
            userActions = ENTITY_FACTORY.createUserActions();
            setId(userActions, rs.getInt("id"));
            userActions.setUserId(rs.getInt("user_id"));
            userActions.setTaskExecuteCount(rs.getInt("task_run"));
            userActions.setPostExecuteCount(rs.getInt("post_count"));
            userActions.setAttachmentExecuteCount(rs.getInt("attachment_count"));
            userActions.setAttachmentTraffic(rs.getInt("attachment_traffic"));
            userActionsList.add(EntitySynchronizedCacheWrapperUtil.wrap(userActions));
        }
        return userActionsList;
    }
}
