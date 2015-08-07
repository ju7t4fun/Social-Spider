package com.epam.lab.spider.controller.utils.validation;

import java.util.Map;

/**
 * @author Yura Kovalik
 */
public interface ValidateResult {
    boolean isValid();
    Map<String,String> getInvalidMessage();
}
