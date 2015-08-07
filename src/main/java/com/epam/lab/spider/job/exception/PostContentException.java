package com.epam.lab.spider.job.exception;

/**
 * @author Yura Kovalik
 */
public class PostContentException extends Exception {
    public PostContentException() {
    }

    public PostContentException(String message) {
        super(message);
    }

    public PostContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostContentException(Throwable cause) {
        super(cause);
    }
}
