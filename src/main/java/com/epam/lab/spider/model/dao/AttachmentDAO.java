package com.epam.lab.spider.model.dao;

import com.epam.lab.spider.model.entity.Attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public interface AttachmentDAO extends CRUD<Attachment> {

    List<Attachment> getByPostId(Connection connection, int id) throws SQLException;

    boolean deleteByPostId(Connection connection, int id) throws SQLException;

}
