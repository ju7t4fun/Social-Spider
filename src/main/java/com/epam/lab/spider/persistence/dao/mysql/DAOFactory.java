package com.epam.lab.spider.persistence.dao.mysql;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.persistence.dao.*;
import com.epam.lab.spider.persistence.dao.savable.SavableDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 * @author Yura Kovalik
 */
public class DAOFactory {

    private static DAOFactory instance = null;
    private Map<Class<?>, BaseDAO> dao = new HashMap<>();
    private Map<Class<? extends PersistenceIdentifiable>, CRUD<? extends PersistenceIdentifiable>> daoByEntity = new HashMap<>();

    private DAOFactory() {
        dao.put(AttachmentDAO.class, new AttachmentDAOImp());
        markDaoByEntity(Attachment.class, new AttachmentDAOImp());
        dao.put(CategoryDAO.class, new CategoryDAOImp());
        markDaoByEntity(Category.class, new CategoryDAOImp());
        dao.put(EventDAO.class, new EventDAOImp());
        markDaoByEntity(Event.class, new EventDAOImp());
        dao.put(FilterDAO.class, new FilterDAOImp());
        markDaoByEntity(Filter.class, new FilterDAOImp());
        dao.put(PostingTaskDAO.class, new PostingTaskDAOImp());
        markDaoByEntity(PostingTask.class, new PostingTaskDAOImp());
        dao.put(OwnerDAO.class, new OwnerDAOImp());
        markDaoByEntity(Owner.class, new OwnerDAOImp());
        dao.put(PostDAO.class, new PostDAOImp());
        markDaoByEntity(Post.class, new PostDAOImp());
        dao.put(TaskDAO.class, new TaskDAOImp());
        markDaoByEntity(Task.class, new TaskDAOImp());
        dao.put(UserDAO.class, new UserDAOImp());
        markDaoByEntity(User.class, new UserDAOImp());
        dao.put(ProfileDAO.class, new ProfileDAOImp());
        markDaoByEntity(Profile.class, new ProfileDAOImp());
        dao.put(WallDAO.class, new WallDAOImp());
        markDaoByEntity(Wall.class, new WallDAOImp());
        dao.put(MessageDAO.class, new MessageDAOImp());

        dao.put(CategoryHasTaskDAO.class, new CategoryHasTaskDAOImp());
        dao.put(TaskSourceDAO.class, new TaskSourceDAOImp());
        dao.put(TaskDestinationDAO.class, new TaskDestinationDAOImp());
        dao.put(CategoryHasPostDAO.class, new CategoryHasPostDAOImp());
        dao.put(UserHasCategoryDAO.class, new UserHasCategoryDAOImp());

        dao.put(TaskHistoryDAO.class, new TaskHistoryDAOImpl());
        dao.put(DataLockDAO.class, new DataLockDAOImpl());
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    private <C extends PersistenceIdentifiable, E extends CRUD<C>> void markDaoByEntity(Class<C> clazz, E entity) {
        daoByEntity.put(clazz, entity);
    }

    public <T> T create(Class<T> daoClass) {
        return (T) dao.get(daoClass);
    }

    public <C extends PersistenceIdentifiable, E extends CRUD<C>> E getCrudDAOByEntity(Class<C> entityClass) {
        return (E) daoByEntity.get(entityClass);
    }

    public <C extends PersistenceIdentifiable, E extends SavableDAO<C>> E getSavableDAO(Class<C> clazz) {
        CRUD localDAO = this.getCrudDAOByEntity(clazz);
        if (localDAO instanceof SavableDAO) {
            return (E) localDAO;
        }
        return null;
    }
}
