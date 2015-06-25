package com.epam.lab.spider.model.db.dao.savable.exception;


/**
 * Created by shell on 6/18/2015.
 */
public class ResolvableDAOException extends Exception {
    public ResolvableDAOException() {
    }

    public ResolvableDAOException(String message) {
        super(message);
    }

    public ResolvableDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResolvableDAOException(Throwable cause) {
        super(cause);
    }



}
