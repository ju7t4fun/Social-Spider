package com.epam.lab.spider.model.db.dao.mysql;

import com.epam.lab.spider.model.db.dao.*;
import com.epam.lab.spider.model.db.entity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class DAOFactory {

    private Map<Class<?>, BaseDAO> dao = new HashMap<>();
    private Map<Class<?>, CRUD<?>> daoByEntity = new HashMap<>();
    private static DAOFactory instance = null;

    private DAOFactory() {
        dao.put(AttachmentDAO.class, new AttachmentDAOImp());
        daoByEntity.put(Attachment.class, new AttachmentDAOImp());
        dao.put(CategoryDAO.class, new CategoryDAOImp());
        daoByEntity.put(Category.class, new CategoryDAOImp());
        dao.put(EventDAO.class, new EventDAOImp());
        daoByEntity.put(Event.class, new EventDAOImp());
        dao.put(FilterDAO.class, new FilterDAOImp());
        daoByEntity.put(Filter.class, new FilterDAOImp());
        dao.put(NewPostDAO.class, new NewPostDAOImp());
        daoByEntity.put(NewPost.class, new NewPostDAOImp());
        dao.put(OwnerDAO.class, new OwnerDAOImp());
        daoByEntity.put(Owner.class, new OwnerDAOImp());
        dao.put(PostDAO.class, new PostDAOImp());
        daoByEntity.put(Post.class, new PostDAOImp());
        dao.put(TaskDAO.class, new TaskDAOImp());
        daoByEntity.put(Task.class, new TaskDAOImp());
        dao.put(UserDAO.class, new UserDAOImp());
        daoByEntity.put(User.class, new UserDAOImp());
        dao.put(ProfileDAO.class, new ProfileDAOImp());
        daoByEntity.put(Profile.class, new ProfileDAOImp());
        dao.put(WallDAO.class, new WallDAOImp());
        daoByEntity.put(Wall.class, new WallDAOImp());

        dao.put(CategoryHasTaskDAO.class, new CategoryHasTaskDAOImp());
        dao.put(TaskSourceDAO.class, new TaskSourceDAOImp());
        dao.put(TaskDestinationDAO.class, new TaskDestinationDAOImp());

        dao.put(TaskSynchronizedDataDAO.class, new TaskSynchronizedDataDAOImpl());
        dao.put(DataLockDAO.class, new DataLockDAOImpl());
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }

    public <T> T create(Class<T> daoClass) {
        return (T) dao.get(daoClass);
    }
    public <E> CRUD<E> getCrudDAOByEntity(Class<E> entityClass){
        return (CRUD<E>) daoByEntity.get(entityClass);
    }

}
