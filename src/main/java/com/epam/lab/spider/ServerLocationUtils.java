package com.epam.lab.spider;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yura Kovalik
 */
public class ServerLocationUtils {

    public static String getDefaultServerPath(){
        return "http://social-copybot.rhcloud.com";
    }

    public static String getServerPath(HttpServletRequest request){
        if(request == null)return getDefaultServerPath();
        String path = "http://"+request.getServerName();
        if(request.getServerPort() != 80){
            path += ":"+request.getServerPort();
        }
        path += request.getContextPath();
        return path;
    }
}
