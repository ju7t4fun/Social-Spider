package com.epam.lab.spider.model.service;

import com.epam.lab.spider.model.PoolConnection;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.PostMetadataDAO;
import com.epam.lab.spider.model.entity.PostMetadata;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Sasha on 12.06.2015.
 */
public class PostMetadataService implements BaseService<PostMetadata> {

    private DAOFactory factory = DAOFactory.getInstance();
    private PostMetadataDAO pmdao = factory.create(PostMetadataDAO.class);

    @Override
    public boolean insert(PostMetadata pm) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.insert(connection, pm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(int id, PostMetadata pm) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.update(connection, id, pm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.delete(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<PostMetadata> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.getAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PostMetadata getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return pmdao.getById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
