package com.epam.lab.spider.model.db.dao;

import com.epam.lab.spider.model.db.dao.savable.SavableDAO;
import com.epam.lab.spider.model.db.entity.Filter;
import com.epam.lab.spider.model.db.entity.Task;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public interface FilterDAO extends CRUD<Filter>,SavableDAO<Filter> {

}
