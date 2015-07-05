package com.epam.lab.spider.job.exception;

/**
 * Created by hell-engine on 7/5/2015.
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
