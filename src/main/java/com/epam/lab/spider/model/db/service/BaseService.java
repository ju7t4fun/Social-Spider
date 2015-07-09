package com.epam.lab.spider.model.db.service;

import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public interface BaseService<T> {

    boolean insert(T t);

    boolean update(int id, T t);

    boolean delete(int id);

    List<T> getAll();

    T getById(int id);

}
