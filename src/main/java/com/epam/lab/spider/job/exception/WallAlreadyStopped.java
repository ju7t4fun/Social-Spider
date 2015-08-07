package com.epam.lab.spider.job.exception;

/**
 * @author Yura Kovalik
 */
public class WallAlreadyStopped extends Exception {
    public WallAlreadyStopped() {
    }

    public WallAlreadyStopped(String message) {
        super(message);
    }

    public WallAlreadyStopped(String message, Throwable cause) {
        super(message, cause);
    }

    public WallAlreadyStopped(Throwable cause) {
        super(cause);
    }
}
