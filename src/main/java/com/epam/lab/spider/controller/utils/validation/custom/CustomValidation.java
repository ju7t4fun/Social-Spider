package com.epam.lab.spider.controller.utils.validation.custom;

/**
 * Created by shell on 6/13/2015.
 */
public interface CustomValidation {
    <T>boolean validate(T object);
}
