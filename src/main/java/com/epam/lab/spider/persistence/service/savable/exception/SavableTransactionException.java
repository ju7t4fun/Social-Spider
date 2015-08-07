package com.epam.lab.spider.persistence.service.savable.exception;

/**
 * @author Yura Kovalik
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
