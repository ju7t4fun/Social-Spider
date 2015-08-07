package com.epam.lab.spider.job.limit;

/**
 * @author Yura Kovalik
 */
public interface UserLimits {

    int getTaskExecuteLimit(Integer userId);

    int getPostExecuteLimit(Integer userId);

    int getAttachmentExecuteLimit(Integer userId);

    int getAttachmentTrafficLimit(Integer userId);
}
