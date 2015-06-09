package com.epam.lab.spider.controller.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Boyarsky Vitaliy on 08.06.2015.
 */
public class UTF8 {

    private UTF8() {
        super();
    }

    public static String encoding(String string) {
        if (string == null)
            return null;
        try {
            return new String(string.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
