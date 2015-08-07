package com.epam.lab.spider.persistence.service;

import java.util.List;

/**
 * @author Boyarsky Vitaliy
 */
public interface BaseService<T> {

    boolean insert(T t);

    boolean update(int id, T t);

    boolean delete(int id);

    List<T> getAll();

    T getById(int id);

}
