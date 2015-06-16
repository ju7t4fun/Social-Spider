package com.epam.lab.spider.job.util;

import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;

/**
 * Created by shell on 6/17/2015.
 */
public class Locker {
    private static Locker ourInstance = new Locker();

    public static Locker getInstance() {
        return ourInstance;
    }

    private Locker() {
    }
    public synchronized void isLock(Wall wall){

    }
    public synchronized void lock(Wall wall){

    }
    public synchronized void unLock(Wall wall){

    }
    public synchronized void isLock(Profile profile){

    }
    public synchronized void lock(Profile profile){

    }
    public synchronized void unLock(Profile profile){

    }
}
