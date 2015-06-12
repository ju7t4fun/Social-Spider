package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;
import com.epam.lab.spider.model.dao.VkProfileDAO;
import com.epam.lab.spider.model.dao.WallDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class DAOFactoryImp implements DAOFactory {

    private Map<Class, BaseDAO> map = new HashMap<>();

    public DAOFactoryImp() {
        map.put(UserDAO.class, new UserDAOImp());
        map.put(VkProfileDAO.class, new VkProfileDAOImp());
        map.put(PostDAO.class, new PostDAOImp());
        map.put(WallDao.class, new WallDaoImp());
        map.put(PostMetadataDAO.class, new PostMetadataDAOImp());
        map.put(FilterDAO.class, new FilterDAOImp());
        map.put(OwnerDAO.class, new OwnerDAOImp());
        map.put(CategoryDAO.class, new CategoryDAOImp());
        map.put(AttachmentDAO.class, new AttachmentDAOImp());
        map.put(NewPostDAO.class, new NewPostDAOImp());
    }

    @Override
    public <T> T create(Class<T> daoClass) {
        return (T) map.get(daoClass);
    }

}
