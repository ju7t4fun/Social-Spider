package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.persistence.PoolConnection;
import com.epam.lab.spider.persistence.dao.mysql.DAOFactory;
import com.epam.lab.spider.persistence.dao.OwnerDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @author Boyarsky Vitaliy
 */
public class OwnerService implements BaseService<Owner> {
    private static final Logger LOG = Logger.getLogger(OwnerService.class);

    private DAOFactory factory = DAOFactory.getInstance();
    private OwnerDAO ownerDAO = factory.create(OwnerDAO.class);

    private WallService wallService = new WallService();


    @Override
    public boolean insert(Owner owner) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.insert(connection, owner);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean update(int id, Owner owner) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.update(connection, id, owner);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            wallService.deleteByOwnerId(connection, id);
            return ownerDAO.delete(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public List<Owner> getAll() {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getAll(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Owner> getLimited(Integer begin, Integer end) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getLimited(connection, begin, end);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public Owner getById(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getById(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public Owner getByVkId(int vk_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getByVkId(connection, vk_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

//    public List<Owner> getWithQuery(String SQL_SOME_QUERY) {
//        try (Connection connection = PoolConnection.getConnection()) {
//            return ownerDAO.getWithQuery(connection, SQL_SOME_QUERY);
//        } catch (SQLException e) {
//            LOG.error(e.getLocalizedMessage(), e);
//        }
//        return null;
//    }

    public int getCountWithQuery(String SQL_SOME_QUERY) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getCountWithQuery(connection, SQL_SOME_QUERY);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Owner> getByUserId(int userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getByUserId(connection, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Owner> getByUserId(Integer id, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getByUserId(connection, id, page, size);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountByUserId(Integer id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getCountByUserId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Owner> searchByUserId(Integer id, String q, int page, int size) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.searchByUserId(connection, id, q, page, size);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public int getCountSearchByUserId(Integer id, String q) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getCountSearchByUserId(connection, id, q);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return -1;
    }

    public List<Owner> getAllGroups(int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getAllGroups(connection, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Owner> getAllGroupsWithSearch(String nameToSearch, int start, int ammount) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getAllGroupsWithSearch(connection, nameToSearch, start, ammount);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean updateBan(int vk_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.updateBan(connection, vk_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean updateUnBan(int vk_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.updateUnBan(connection, vk_id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public int getCountAllUnique() {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getCountAllUnique(connection);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public int getCountAllUniqueWithSearch(String nameToSearch) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.getCountAllUniqueWithSearch(connection, nameToSearch);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    public boolean deleteByVkId(int vk_id) {
        try (Connection connection = PoolConnection.getConnection()) {
            List<Owner> owners = ownerDAO.getAllByVkId(connection, vk_id);
            for (Owner owner : owners) {
                delete(owner.getId());
            }
            return true;
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean isBannedByVkId(int id) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.isBannedByVkId(connection, id);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean hasUserGroup(int id, Integer userId) {
        try (Connection connection = PoolConnection.getConnection()) {
            return ownerDAO.hasUserGroup(connection, id, userId);
        } catch (SQLException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
