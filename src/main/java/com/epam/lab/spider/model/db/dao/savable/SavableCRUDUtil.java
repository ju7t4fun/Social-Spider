package com.epam.lab.spider.model.db.dao.savable;

import com.epam.lab.spider.controller.utils.validation.ValidateResult;
import com.epam.lab.spider.controller.utils.validation.Validator;
import com.epam.lab.spider.model.db.dao.CRUD;
import com.epam.lab.spider.model.db.dao.mysql.BaseDAO;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shell on 6/18/2015.
 */
public class SavableCRUDUtil {
    public static final boolean VALIDATION_SUPPORT = true;
    private static Map<Class,Method> idFieldFromClassMap = new HashMap<>();

    private static Integer getId(Object entity) throws InvalidEntityException {
        Class  clazz = entity.getClass();
        Method method = idFieldFromClassMap.get(clazz);
        try {
            if(method ==null){
                method = clazz.getDeclaredMethod("getId", new Class[0]);
                idFieldFromClassMap.put(clazz,method);
            }
            Object result =  method.invoke(entity, new Object[0]);
            return (Integer) result;

        } catch (NoSuchMethodException|ClassCastException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new InvalidEntityException("Can not find ID!");
    }
    public static boolean  safeSave(Connection conn,Object entity,BaseDAO dao){
        try {
            save(conn,entity,dao);
        } catch (InvalidEntityException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedDAOException e) {
            e.printStackTrace();
            return false;
        } catch (ResolvableDAOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean save(Connection conn,Object entity, BaseDAO dao) throws UnsupportedDAOException, ResolvableDAOException, InvalidEntityException {
        if (entity == null) return false;
        if (VALIDATION_SUPPORT) {
            Validator validator = new Validator();
            ValidateResult validateResult = validator.valideteWithResult(entity);
            if(!validateResult.isValid()){
                InvalidEntityException exception = new InvalidEntityException("Invalid entity");
                exception.setValidateResult(validateResult);
                throw exception;
            }
        }
        CRUD crudDAO;
        try{
            crudDAO = (CRUD) dao;
        }catch (ClassCastException x){
            throw new UnsupportedDAOException(""+dao.getClass().getName()+" must implement "+CRUD.class.getName(),x);
        }

        if (!(dao instanceof SavableDAO)) {
            throw new UnsupportedDAOException("" + crudDAO.getClass().getName() + " must implements " + SavableDAO.class.getName());
        }
        try{
            Integer index = getId(entity);
            //if(entity.id!=null)
            if (index != null) {
                crudDAO.update(conn, index, entity);
            } else {
                crudDAO.insert(conn, entity);
            }
        }catch (SQLException x){
            throw new ResolvableDAOException(x);
        }
        return false;
    }


}
