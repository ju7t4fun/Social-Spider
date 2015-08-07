package com.epam.lab.spider.integration.vk.auth;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Boyarsky Vitaliy
 */
public class AccessToken implements OAuth {

    private String accessToken;
    private Date expirationMoment;
    private int userId;
    private String email;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() < expirationMoment.getTime();
    }

    @Override
    public Date getExpirationMoment() {
        return expirationMoment;
    }

    @Override
    public void setExpirationMoment(long time) {
        expirationMoment = new Date(System.currentTimeMillis()
                + TimeUnit.SECONDS.toMillis(time));
    }

    @Override
    public void setExpirationMoment(Date date) {
        expirationMoment = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessToken: {")
                .append("user_id='").append(userId).append("', ")
                .append("access_token='").append(accessToken).append("', ")
                .append("expiration_moment='").append(expirationMoment).append("'}");
        return sb.toString();
    }

}
