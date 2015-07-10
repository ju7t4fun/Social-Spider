package com.epam.lab.spider.job.exception;

/**
 * Created by hell-engine on 7/10/2015.
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
