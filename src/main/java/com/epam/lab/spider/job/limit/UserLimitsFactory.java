package com.epam.lab.spider.job.limit;

/**
 * Created by hell-engine on 7/24/2015.
 */
public class UserLimitsFactory {
    private static UserLimit userLimit = new SimpleUserLimit();
    public static UserLimit getUserLimit() {
        return userLimit;
    }
}
