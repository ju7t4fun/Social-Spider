package com.epam.lab.spider.job.limit;

import com.epam.lab.spider.model.db.entity.User;

/**
 * Created by hell-engine on 7/22/2015.
 */
public interface UserLimitUtil {

    abstract int getTaskExecuteLimit(User user);

    abstract int getPostExecuteLimit(User user);

    abstract int getAttachmentExecuteLimit(User user);

    abstract int getAttachmentTrafficLimit(User user);
}
