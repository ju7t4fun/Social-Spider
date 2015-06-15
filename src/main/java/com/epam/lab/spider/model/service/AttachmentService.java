package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.AttachmentDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.entity.Attachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class AttachmentService implements BaseService<Attachment> {

    private DAOFactory factory = DAOFactory.getInstance();
    private AttachmentDAO adao = factory.create(AttachmentDAO.class);

    @Override
    public boolean insert(Attachment attachment) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.insert(connection, attachment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, Attachment attachment) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.update(connection, id, attachment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Attachment> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Attachment getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attachment> getByPostId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.getByPostId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteByPostId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.deleteByPostId(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
