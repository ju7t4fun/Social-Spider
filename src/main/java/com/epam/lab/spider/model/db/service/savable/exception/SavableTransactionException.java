package com.epam.lab.spider.model.db.service.savable.exception;

/**
 * Created by shell on 6/30/2015.
 */
public class SavableTransactionException extends RuntimeException {
    public SavableTransactionException() {
    }

    public SavableTransactionException(String message) {
        super(message);
    }

    public SavableTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavableTransactionException(Throwable cause) {
        super(cause);
    }
}
