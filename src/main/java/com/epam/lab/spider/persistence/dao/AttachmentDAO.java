package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */

public interface AttachmentDAO extends CRUD<Attachment>, SavableDAO<Attachment> {

    List<Attachment> getByPostId(Connection connection, int id) throws SQLException;

    boolean deleteByPostId(Connection connection, int id) throws SQLException;


}
