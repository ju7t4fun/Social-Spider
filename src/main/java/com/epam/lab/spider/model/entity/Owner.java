package com.epam.lab.spider.model.entity;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Owner {
    private Integer id;
    private Integer vk_id;
    private String name;
    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVk_id() {
        return vk_id;
    }

    public void setVk_id(Integer vk_id) {
        this.vk_id = vk_id;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "domain='" + domain + '\'' +
                ", id=" + id +
                ", vk_id=" + vk_id +
                ", name='" + name + '\'' +
                '}';
    }
}
