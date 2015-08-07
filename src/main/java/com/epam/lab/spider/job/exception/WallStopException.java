package com.epam.lab.spider.job.exception;

/**
 * @author Yura Kovalik
 */
public class WallStopException extends Exception {
    public WallStopException() {
    }

    public WallStopException(String message) {
        super(message);
    }

    public WallStopException(String message, Throwable cause) {
        super(message, cause);
    }

    public WallStopException(Throwable cause) {
        super(cause);
    }
}
