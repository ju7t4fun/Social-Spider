package com.epam.lab.spider.model.dao.mysql;

import com.epam.lab.spider.model.dao.BaseDAO;
import com.epam.lab.spider.model.dao.DAOFactory;
import com.epam.lab.spider.model.dao.UserDAO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class DAOFactoryImp implements DAOFactory {

    private Map<Class, BaseDAO> map = new HashMap<>();

    public DAOFactoryImp() {
        map.put(UserDAO.class, new UserDAOImp());
    }

    @Override
    public <T> T create(Class<T> daoClass) {
        return (T) map.get(daoClass);
    }

}
