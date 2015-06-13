package com.epam.lab.spider.controller.utils.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by shell on 6/13/2015.
 */

@Target( ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    String message() default "{jcom.epam.lab.spider.controller.utils.validation.annotation.Size.message}";


    int min() default 0;

    int max() default 2147483647;
}