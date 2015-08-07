package com.epam.lab.spider.persistence.dao;

import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.persistence.dao.savable.SavableDAO;

/**
 * @author Marian Voronovskyi
 */
public interface FilterDAO extends CRUD<Filter>, SavableDAO<Filter> {

}
