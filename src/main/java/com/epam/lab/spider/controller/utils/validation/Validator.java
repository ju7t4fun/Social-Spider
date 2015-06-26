package com.epam.lab.spider.controller.utils.validation;

import com.epam.lab.spider.controller.utils.validation.annotation.*;
import com.epam.lab.spider.controller.utils.validation.annotation.Pattern;
import com.epam.lab.spider.controller.utils.validation.custom.CustomValidation;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

/**
 * Created by shell on 6/13/2015.
 */
public class Validator {
    public boolean isValid(Object valideObject){
        Class clazz = valideObject.getClass();
        Field[] fields =  clazz.getDeclaredFields();
        for(Field field:fields){
            boolean realAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                Object local = field.get(valideObject);
                NotNull notNull = field.getAnnotation(NotNull.class);
                if(notNull != null){

                    if(local==null)return false;
                    else if(local.toString().isEmpty())return false;
                }
                if(local!=null) {
                    Pattern pattern = field.getAnnotation(Pattern.class);
                    if (pattern != null) {
                        String localString = local.toString();

                        boolean result;
                        if (pattern.flags().length > 0) {
                            int flags = 0;
                            for (Pattern.Flag flag : pattern.flags()) {
                                flags |= flag.getValue();
                            }
                            Matcher mather = java.util.regex.Pattern.compile(pattern.regexp(), flags).matcher(localString);
                            result = mather.find();

                        } else {
                            result = localString.matches(pattern.regexp());
                        }

                        if (!result) {
                            return false;
                        }

                    }
                    Size size = field.getAnnotation(Size.class);
                    if (size != null) {
                        if (local instanceof String) {
                            String string = (String) local;
                            if (string.length() < size.min() || string.length() > size.max()) {
                                return false;
                            }
                        }
                    }
                    AssertCustom assertCustom = field.getAnnotation(AssertCustom.class);
                    if (assertCustom != null) {
                        CustomValidation customValidation = assertCustom.clazz().newInstance();
                        boolean result = customValidation.validate(local);
                        if (!result) {
                            return false;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return false;
            }
            field.setAccessible(realAccessible);
        }

        return true;
    }
    public ValidateResult valideteWithResult(Object valideObject){
        DefaultValidateResult validateResult = new DefaultValidateResult();


        Class clazz = valideObject.getClass();
        Field[] fields =  clazz.getDeclaredFields();
        for(Field field:fields){
            boolean realAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                Object local = field.get(valideObject);
                NotNull notNull = field.getAnnotation(NotNull.class);
                if(notNull != null){
                    if(local==null){
                        validateResult.add(field.getName(),notNull.message());
                    }
                    else if(local.toString().isEmpty()){
                        validateResult.add(field.getName(),notNull.message());
                    }

                }
                if(local!=null){
                    Pattern pattern = field.getAnnotation(Pattern.class);
                    if (pattern != null) {
                        String localString = local.toString();

                        boolean result;
                        if (pattern.flags().length > 0) {
                            int flags = 0;
                            for (Pattern.Flag flag : pattern.flags()) {
                                flags |= flag.getValue();
                            }
                            Matcher mather = java.util.regex.Pattern.compile(pattern.regexp(), flags).matcher(localString);
                            result = mather.find();

                        } else {
                            result = localString.matches(pattern.regexp());
                        }

                        if (!result) {
                            validateResult.add(field.getName(), pattern.message());
                        }

                    }
                    Size size = field.getAnnotation(Size.class);
                    if (size != null) {

                        if (local instanceof String) {
                            String string = (String) local;
                            if (string.length() < size.min() || string.length() > size.max()) {
                                validateResult.add(field.getName(), size.message());
                            }
                        }
                    }
                    AssertCustom assertCustom = field.getAnnotation(AssertCustom.class);
                    if (assertCustom != null) {
                        CustomValidation customValidation = assertCustom.clazz().newInstance();

                        boolean result = customValidation.validate(local);
                        if (!result) {
                            validateResult.add(field.getName(), assertCustom.message());
                        }
                    }
                }
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                validateResult.add(field.getName(),"Inner Error");
            }
            field.setAccessible(realAccessible);
        }


        return validateResult;
    }
    final static class DefaultValidateResult implements ValidateResult{
        Map<String, String> errors = new HashMap<>();
        boolean valid = true;
        @Override
        public boolean isValid() {
            return valid;
        }

        public void add(String key, String value) {
            valid = false;
            errors.put(key, value);
        }

        @Override
        public Map<String, String> getInvalidMessage() {
            return errors;
        }
    }
}
