package com.epam.lab.spider.controller.utils;

/**
 * Created by Marian Voronovskyi on 12.06.2015.
 */
public class Encryptor {

    public static String encoding(String in) {
        StringBuilder builder = new StringBuilder();
        for (char c : in.toCharArray()) {
            builder.append(String.format("%02x", (int) c));
        }
        return builder.toString();
    }

    public static String decoding(String in) {
        StringBuilder builder = new StringBuilder();
        char[] chars = in.toCharArray();
        for (int i = 0; i < in.length(); i = i + 2) {
            builder.append((char) Integer.parseInt("" + chars[i] + chars[i + 1], 16));
        }
        return builder.toString();
    }
}
