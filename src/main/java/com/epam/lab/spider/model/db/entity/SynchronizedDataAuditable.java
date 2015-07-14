package com.epam.lab.spider.model.db.entity;

import com.epam.lab.spider.model.vk.*;

/**
 * Created by hell-engine on 7/12/2015.
 */
public interface SynchronizedDataAuditable extends SynchronizedData {


    Integer getRevisionCount();

    void setRevisionCount(Integer revisionCount);
}
