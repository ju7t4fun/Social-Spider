package com.epam.lab.spider.controller.utils.validation.custom;

/**
 * @author Yura Kovalik
 */
public interface CustomValidation {
    <T>boolean validate(T object);
}
