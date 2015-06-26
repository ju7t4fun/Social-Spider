package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.PoolConnection;
import com.epam.lab.spider.model.db.dao.AttachmentDAO;
import com.epam.lab.spider.model.db.dao.CRUD;
import com.epam.lab.spider.model.db.dao.mysql.DAOFactory;
import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.service.savable.SavableService;
import com.epam.lab.spider.model.db.service.savable.SavableServiceUtil;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class AttachmentService implements BaseService<Attachment>,SavableService<Attachment> {

    private DAOFactory factory = DAOFactory.getInstance();
    private AttachmentDAO adao = factory.create(AttachmentDAO.class);


    @Override
    public boolean save(Attachment entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.saveFromInterface(entity,this);
    }

    @Override
    public boolean save(Attachment entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiseException {
        return SavableServiceUtil.customSave(conn, entity);
    }
    @Deprecated
    @Override
    public boolean insert(Attachment attachment) {
        try (Connection connection = PoolConnection.getConnection()) {
            return adao.insert(connection, attachment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Deprecated
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
