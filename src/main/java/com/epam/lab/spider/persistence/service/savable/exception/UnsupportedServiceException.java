package com.epam.lab.spider.persistence.service.savable.exception;

/**
 * @author Yura Kovalik
 */
public class UnsupportedServiceException extends Exception {
    public UnsupportedServiceException() {
    }

    public UnsupportedServiceException(String message) {
        super(message);
    }

    public UnsupportedServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedServiceException(Throwable cause) {
        super(cause);
    }
}
