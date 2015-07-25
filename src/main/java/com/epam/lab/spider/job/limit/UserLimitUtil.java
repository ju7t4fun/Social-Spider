package com.epam.lab.spider.job.limit;

import com.epam.lab.spider.model.db.entity.User;

/**
 * Created by hell-engine on 7/22/2015.
 */
public interface UserLimitUtil {

    int getTaskExecuteLimit(Integer userId);

    int getPostExecuteLimit(Integer userId);

    int getAttachmentExecuteLimit(Integer userId);

    int getAttachmentTrafficLimit(Integer userId);
}
