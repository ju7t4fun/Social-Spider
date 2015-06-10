package com.epam.lab.spider.controller.oauth;

import java.util.Date;

public interface OAuth {

    void setAccessToken(String token);
    String getAccessToken();
    boolean isExpired();
    void setExpirationMoment(long time);
    Date getExpirationMoment();

}
