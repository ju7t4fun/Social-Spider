package com.epam.lab.spider.controller.utils;

/**
 * Created by Marian Voronovskyi on 11.07.2015.
 */
public class ReplaceHtmlTags {
    public static String reaplaceAll(String text) {
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
