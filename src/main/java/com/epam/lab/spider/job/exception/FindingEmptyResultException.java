package com.epam.lab.spider.job.exception;

/**
 * Created by hell-engine on 7/11/2015.
 */
public class FindingEmptyResultException extends Exception {
    private Integer vkId;
    private Integer offset;

    public FindingEmptyResultException(Integer vkId, Integer offset) {
        this.vkId = vkId;
        this.offset = offset;
    }

    public Integer getVkId() {
        return vkId;
    }

    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
