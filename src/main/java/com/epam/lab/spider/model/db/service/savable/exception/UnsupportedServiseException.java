package com.epam.lab.spider.model.db.service.savable.exception;

/**
 * Created by shell on 6/19/2015.
 */
public class UnsupportedServiseException extends Exception {
    public UnsupportedServiseException() {
    }

    public UnsupportedServiseException(String message) {
        super(message);
    }

    public UnsupportedServiseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedServiseException(Throwable cause) {
        super(cause);
    }
}
