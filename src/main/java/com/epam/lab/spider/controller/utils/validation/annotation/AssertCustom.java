package com.epam.lab.spider.controller.utils.validation.annotation;
import com.epam.lab.spider.controller.utils.validation.custom.CustomValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by shell on 6/13/2015.
 */

@Target( ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AssertCustom {
    String message() default "{com.epam.lab.spider.controller.utils.validation.annotation.AssertCustom.message}";

    Class<? extends CustomValidation> clazz();
}
