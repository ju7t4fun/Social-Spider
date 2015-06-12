package com.epam.lab.spider.model.dao;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public interface DAOFactory {

    <T> T create(Class<T> clazz);

}
