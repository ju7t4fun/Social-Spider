package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Filter;
import com.epam.lab.spider.model.entity.Wall;
import com.epam.lab.spider.model.entity.impl.TaskImpl;
import com.epam.lab.spider.persistence.service.FilterService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.WallService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindTaskImp extends TaskImpl {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService wallService = PersistenceBindTaskImp.factory.create(WallService.class);
    private static FilterService filterService = PersistenceBindTaskImp.factory.create(FilterService.class);


    @Override
    public Set<Wall> getDestination() {
        if (super.getDestination() == null) {
            if (getId() == null)
                setDestination(new HashSet<Wall>());
            else
                setDestination(new HashSet<>(wallService.getDestinationByTaskId(getId())));
        }
        return super.getDestination();
    }

    public Set<Wall> getSource() {
        if (super.getSource() == null) {
            if (getId() == null)
                setSource(new HashSet<Wall>());
            else
                setSource(new HashSet<>(wallService.getSourceByTaskId(getId())));
        }
        return super.getSource();
    }

    public Filter getFilter() {
        if (super.getFilter() == null) {
            if (super.getFilterId() == null)
                setFilter(PersistenceEntityFactory.getInstance().createFilter());
            else
                setFilter(filterService.getById(super.getFilterId()));
        }
        return super.getFilter();
    }
}
