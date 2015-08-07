package com.epam.lab.spider.persistence.dao.savable.exception;


/**
 * @author Yura Kovalik
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
