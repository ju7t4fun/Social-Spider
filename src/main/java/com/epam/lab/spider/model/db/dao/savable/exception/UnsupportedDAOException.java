package com.epam.lab.spider.model.db.dao.savable.exception;

/**
 * Created by shell on 6/18/2015.
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
