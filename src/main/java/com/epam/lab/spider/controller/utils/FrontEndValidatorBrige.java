package com.epam.lab.spider.controller.utils;

import com.epam.lab.spider.controller.utils.validation.annotation.Pattern;
import com.epam.lab.spider.controller.utils.validation.annotation.Size;

import java.lang.reflect.Field;

/**
 * Created by shell on 6/13/2015.
 */
public class FrontEndValidatorBrige {
    private Package aPackage = Package.getPackage("com.epam.lab.spider.model.entity");

    private String firstCharToUpperCase(String string){
        char charAtLowerCase = string.charAt(0);
        char charAtUpperCase = Character.toUpperCase(charAtLowerCase);
        return  ""+ charAtUpperCase + string.substring(1);
    }

    private String resolveUnjavaStyle(String name){
        String[] fragment = name.split("_");
        if(fragment.length>1) {
            StringBuilder sb = new StringBuilder();
            sb.append(fragment[0]);
            for (int i = 1; i < fragment.length ; i++) {
                sb.append(firstCharToUpperCase(fragment[i]));
            }
            return sb.toString();
        }else  return  name;
    }

    private String resolveClassName(String codeStyleName){
        return firstCharToUpperCase(resolveUnjavaStyle(codeStyleName));
    }


    public String getFrontEndValidationCodeByFieldPath(String fieldPath){
        String[] pathParts = fieldPath.split("\\.");

        if(pathParts.length!=2)return "";

        try {
            Class clazz = Class.forName(aPackage.getName() +"."+ resolveClassName(pathParts[0]));
            String fieldName = resolveUnjavaStyle(pathParts[1]);
            System.out.println(fieldName);
            Field field = clazz.getDeclaredField(fieldName);
            StringBuilder sb = new StringBuilder();
            Size size= field.getAnnotation(Size.class);
            if(size!=null){
                if(size.min()>0){
                    sb.append("").append(" ");
                }
                if(size.max()<65000){
                    sb.append("maxlength=\"").append(size.max()).append("\" ");

                }
            }
            Pattern pattern = field.getAnnotation(Pattern.class);
            if(pattern!=null){
                sb.append("pattern=\"").append(pattern.regexp()).append("\" ");
            }

            return sb.toString();
        } catch (ClassNotFoundException|NoSuchFieldException  e) {
            e.printStackTrace();
            return "";
        }



    }


}
