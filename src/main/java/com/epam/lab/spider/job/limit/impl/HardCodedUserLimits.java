package com.epam.lab.spider.job.limit.impl;

import com.epam.lab.spider.job.limit.UserLimits;

/**
 * @author Yura Kovalik
 */
public class HardCodedUserLimits implements UserLimits {
    private static final int TASK_EXECUTE_LIMIT = 10;
    private static final int POST_EXECUTE_LIMIT = 30;
    private static final int ATTACHMENT_EXECUTE_LIMIT = 50;
    private static final int ATTACHMENT_TRAFFIC_LIMIT = 10*1024;

    public int getTaskExecuteLimit(Integer userId) {
        return TASK_EXECUTE_LIMIT;
    }

    public int getPostExecuteLimit(Integer userId) {
        return POST_EXECUTE_LIMIT;
    }

    public int getAttachmentExecuteLimit(Integer userId) {
        return ATTACHMENT_EXECUTE_LIMIT;
    }

    public int getAttachmentTrafficLimit(Integer userId) {
        return ATTACHMENT_TRAFFIC_LIMIT;
    }
}
