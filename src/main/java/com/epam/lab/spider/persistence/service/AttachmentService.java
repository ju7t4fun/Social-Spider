package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Attachment;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.AttachmentDAO;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.persistence.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.persistence.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.SavableServiceUtil;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Oleksandra Lobanok
 */
public class AttachmentService implements BaseService<Attachment>, SavableService<Attachment> {
    private static final Logger LOG = Logger.getLogger(AttachmentService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private AttachmentDAO attachmentDAO = factory.create(AttachmentDAO.class);


    @Override
    public boolean save(Attachment entity) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.saveFromInterface(entity, this);
    }

    @Override
    public boolean save(Attachment entity, Connection conn) throws InvalidEntityException, UnsupportedDAOException, ResolvableDAOException, UnsupportedServiceException {
        return SavableServiceUtil.customSave(conn, entity);
    }

    @Deprecated
    @Override
    public boolean insert(Attachment attachment) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.insert(connection, attachment);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Deprecated
    @Override
    public boolean update(int id, Attachment attachment) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.update(connection, id, attachment);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.delete(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public List<Attachment> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Attachment getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Attachment> getByPostId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.getByPostId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean deleteByPostId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return attachmentDAO.deleteByPostId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
