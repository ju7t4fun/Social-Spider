package com.epam.lab.spider.model.entity;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Filter {
    private Integer id;
    private Integer likes;
    private Integer reposts;
    private Long min_time;
    private Long max_time;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReposts() {
        return reposts;
    }

    public void setReposts(Integer reposts) {
        this.reposts = reposts;
    }

    public Long getMin_time() {
        return min_time;
    }

    public void setMin_time(Long min_time) {
        this.min_time = min_time;
    }

    public Long getMax_time() {
        return max_time;
    }

    public void setMax_time(Long max_time) {
        this.max_time = max_time;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "id=" + id +
                ", likes=" + likes +
                ", reposts=" + reposts +
                ", min_time=" + min_time +
                ", max_time=" + max_time +
                '}';
    }
}
