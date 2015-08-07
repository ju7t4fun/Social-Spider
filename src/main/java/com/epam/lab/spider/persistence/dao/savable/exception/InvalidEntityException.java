package com.epam.lab.spider.persistence.dao.savable.exception;

import com.epam.lab.spider.controller.utils.validation.ValidateResult;

/**
 * @author Yura Kovalik
 */
public class InvalidEntityException extends Exception {
    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(Throwable cause) {
        super(cause);
    }
    private String validateResultToString(){
        return validateResult.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " " + validateResultToString();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage() + " " + validateResultToString();
    }

    public ValidateResult getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(ValidateResult validateResult) {
        this.validateResult = validateResult;
    }

    private ValidateResult validateResult = null;
}
