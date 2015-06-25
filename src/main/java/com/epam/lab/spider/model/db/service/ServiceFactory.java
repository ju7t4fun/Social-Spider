package com.epam.lab.spider.model.db.service;

import com.epam.lab.spider.model.db.entity.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boyarsky Vitaliy on 12.06.2015.
 */
public class ServiceFactory {

    private Map<Class<? extends BaseService>, BaseService> services = new HashMap<>();
    private Map<Class<?>, BaseService> servicesByClass = new HashMap<>();
    private static ServiceFactory instance = null;

    private ServiceFactory() {
        services.put(AttachmentService.class, new AttachmentService());
        servicesByClass.put(Attachment.class, new AttachmentService());
        services.put(CategoryService.class, new CategoryService());
        servicesByClass.put(Category.class, new CategoryService());
        services.put(EventService.class, new EventService());
        servicesByClass.put(Event.class, new EventService());
        services.put(FilterService.class, new FilterService());
        servicesByClass.put(Filter.class, new FilterService());
        services.put(NewPostService.class, new NewPostService());
        servicesByClass.put(NewPost.class, new NewPostService());
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
}   }
