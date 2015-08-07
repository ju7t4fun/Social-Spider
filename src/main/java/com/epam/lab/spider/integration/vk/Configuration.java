package com.epam.lab.spider.integration.vk;

public class Configuration {

    private int appId;
    private String permissions, secretKey;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(Scope... args) {
        for (Scope i : args)
            permissions = permissions == null ? i.toString() : permissions + "," + i;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {")
                .append("app_id='").append(appId).append("', ")
                .append("permissions='").append(permissions).append("', ")
                .append("secret_key='").append(secretKey).append("'}");
        return sb.toString();
    }

}
