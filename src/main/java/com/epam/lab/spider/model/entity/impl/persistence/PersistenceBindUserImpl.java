package com.epam.lab.spider.model.entity.impl.persistence;

import com.epam.lab.spider.model.entity.Profile;
import com.epam.lab.spider.model.entity.Task;
import com.epam.lab.spider.model.entity.impl.UserImpl;
import com.epam.lab.spider.persistence.service.ProfileService;
import com.epam.lab.spider.persistence.service.ServiceFactory;
import com.epam.lab.spider.persistence.service.TaskService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yura Kovalik
 */
public class PersistenceBindUserImpl extends UserImpl {
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final TaskService taskService = PersistenceBindUserImpl.factory.create(TaskService.class);
    private static final ProfileService profileService = PersistenceBindUserImpl.factory.create(ProfileService.class);

    @Override public Set<Task> getTasks() {
        if (super.getTasks() == null) {
            if (getId() == null)
                setTasks( new HashSet<Task>());
            else
                setTasks( new HashSet<>(taskService.getByUserId(getId())));
        }
        return super.getTasks();
    }

    @Override public Set<Profile> getProfiles() {
        if (super.getProfiles() == null) {
            if (getId() == null)
                setProfiles( new HashSet<Profile>());
            else
                setProfiles( new HashSet<>(profileService.getByUserId(getId())));
        }
        return super.getProfiles();
    }
}
