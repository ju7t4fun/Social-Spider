package com.epam.lab.spider.model.db.service.savable;

import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;
import org.apache.log4j.Logger;


/**
 * Created by shell on 6/19/2015.
 */
public aspect SavableServiceFactoryAspect {
    public static  final Logger LOG = Logger.getLogger(SavableServiceFactoryAspect.class);
    public <E> SavableService<E> ServiceFactory.getSavableService(Class<E> clazz) throws UnsupportedServiseException {
        try{
            BaseService<E> service = this.getByClass(clazz);
            if(service instanceof SavableService){
                return (SavableService<E>) service;
            }else{
                String message = ("For class "+clazz.getName()+" has found service "+service.getClass().getName() +" but it not implements "+SavableService.class.getName());
                throw new UnsupportedServiseException(message);
            }
        }catch (NullPointerException x){
            String message = ("For class "+clazz.getName()+" has not found service");
            throw new UnsupportedServiseException(message,x);
        }
    }
}
