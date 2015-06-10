package com.epam.lab.spider.controller.oauth;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OAuthConfiguration {

    int width = 640;
    int height = 480;
    Image icon;
    String authorization, response, token;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setIcon(String icon) {
        try {
            InputStream input = new FileInputStream(new File(icon));
            this.icon = new Image(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return getClass().toString();
    }

}
