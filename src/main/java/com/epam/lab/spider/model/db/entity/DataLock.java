package com.epam.lab.spider.model.db.entity;

/**
 * Created by shell on 6/20/2015.
 */
public class DataLock {
    public enum Mode{
        DEFAULT, AUTH_KEY, CAPTCHA, ACCESS_DENY, POST_LIMIT
    }
    private String table;
    private Integer index;
    private Mode mode;

    public DataLock(String table, Integer index, Mode mode) {
        this.table = table;
        this.index = index;
        this.mode = mode;
    }

    public String getTable() {
        return table;
    }

    public Integer getIndex() {
        return index;
    }

    public Mode getMode() {
        return mode;
    }
}
