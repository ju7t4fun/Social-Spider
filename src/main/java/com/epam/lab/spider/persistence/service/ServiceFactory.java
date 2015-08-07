package com.epam.lab.spider.persistence.service;

import com.epam.lab.spider.model.PersistenceIdentifiable;
import com.epam.lab.spider.model.entity.*;
import com.epam.lab.spider.model.entity.impl.CategoryImpl;
import com.epam.lab.spider.model.entity.impl.EventImpl;
import com.epam.lab.spider.model.entity.impl.FilterImpl;
import com.epam.lab.spider.model.entity.impl.PostingTaskImpl;
import com.epam.lab.spider.persistence.service.savable.SavableService;
import com.epam.lab.spider.persistence.service.savable.exception.UnsupportedServiceException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Boyarsky Vitaliy
 */
public class ServiceFactory {

    private static ServiceFactory instance = null;
    private Map<Class<? extends BaseService>, BaseService> services = new HashMap<>();
    private Map<Class<? extends PersistenceIdentifiable>, BaseService> servicesByClass = new HashMap<>();

    private ServiceFactory() {
        services.put(AttachmentService.class, new AttachmentService());
        servicesByClass.put(Attachment.class, new AttachmentService());
        services.put(CategoryService.class, new CategoryService());
        servicesByClass.put(CategoryImpl.class, new CategoryService());
        services.put(EventService.class, new EventService());
        servicesByClass.put(EventImpl.class, new EventService());
        services.put(FilterService.class, new FilterService());
        servicesByClass.put(FilterImpl.class, new FilterService());
        services.put(PostingTaskService.class, new PostingTaskService());
        servicesByClass.put(PostingTaskImpl.class, new PostingTaskService());
        services.put(OwnerService.class, new OwnerService());
        servicesByClass.put(Owner.class, new OwnerService());
        services.put(PostService.class, new PostService());
        servicesByClass.put(Post.class, new PostService());
        services.put(TaskService.class, new TaskService());
        servicesByClass.put(Task.class, new TaskService());
        services.put(UserService.class, new UserService());
        servicesByClass.put(User.class, new UserService());
        services.put(ProfileService.class, new ProfileService());
        servicesByClass.put(Profile.class, new ProfileService());
        services.put(WallService.class, new WallService());
        servicesByClass.put(Wall.class, new WallService());
        services.put(MessageService.class, new MessageService());

    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }

    public <T extends BaseService> T create(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }
    public <T>  BaseService<T> getByClass(Class<T> serviceClass){
        return  servicesByClass.get(serviceClass);
}

    public <C extends PersistenceIdentifiable, E extends SavableService<C>> E getSavableService(Class<C> clazz) throws UnsupportedServiceException {
        try{
            BaseService<C> service = this.getByClass(clazz);
            if(service instanceof SavableService){
                return (E) service;
            }else{
                String message = ("For class "+clazz.getName()+" has found service "+service.getClass().getName() +" but it not implements "+SavableService.class.getName());
                throw new UnsupportedServiceException(message);
            }
        }catch (NullPointerException x){
            String message = ("For class "+clazz.getName()+" has not found service");
            throw new UnsupportedServiceException(message, x);
        }
    }
}

