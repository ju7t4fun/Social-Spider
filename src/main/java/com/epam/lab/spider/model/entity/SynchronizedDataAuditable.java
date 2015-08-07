package com.epam.lab.spider.model.entity;

/**
 * @author Yura Kovalik
 */
public interface SynchronizedDataAuditable extends SynchronizedData {


    Integer getRevisionCount();

    void setRevisionCount(Integer revisionCount);
}
