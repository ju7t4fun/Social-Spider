package com.epam.lab.spider.integration.vk.auth;

import java.util.Date;

public interface OAuth {

    String getAccessToken();

    void setAccessToken(String token);

    boolean isExpired();

    void setExpirationMoment(long time);

    Date getExpirationMoment();

    void setExpirationMoment(Date date);

}
