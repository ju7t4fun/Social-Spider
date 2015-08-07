package com.epam.lab.spider.job.limit;

import com.epam.lab.spider.job.limit.impl.SimpleUserLimitProcessor;

/**
 * @author Yura Kovalik
 */
public class UserLimitsFactory {
    private static UserLimitProcessor userLimitProcessor = new SimpleUserLimitProcessor();

    public static UserLimitProcessor getUserLimitProcessor() {
        return userLimitProcessor;
    }
}
