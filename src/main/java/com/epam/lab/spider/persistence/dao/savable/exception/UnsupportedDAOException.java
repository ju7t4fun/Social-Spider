package com.epam.lab.spider.persistence.dao.savable.exception;

/**
 * @author Yura Kovalik
 */
public class UnsupportedDAOException extends Exception {
    public UnsupportedDAOException() {
    }

    public UnsupportedDAOException(String message) {
        super(message);
    }

    public UnsupportedDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDAOException(Throwable cause) {
        super(cause);
    }
}
