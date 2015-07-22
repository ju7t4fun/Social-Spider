package com.epam.lab.spider.job.limit;

import com.epam.lab.spider.model.db.entity.User;

/**
 * Created by hell-engine on 7/22/2015.
 */
public interface UserLimit {
    void markTaskExecute(User user);
    void markPostExecute(User user);
    void markAttachmentExecute(User user);

    boolean checkTaskExecute(User user);
    boolean checkPostExecute(User user);
    boolean checkAttachmentExecute(User user);

    void markAttachmentTraffic(User user, int traffic);
    boolean checkAttachmentTraffic(User user);
    boolean checkAttachmentTraffic(User user, int withNextTraffic);
}
