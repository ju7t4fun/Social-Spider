package com.epam.lab.spider.controller.utils;

/**
 * @author Marian Voronovskyi
 */
public class ReplaceHtmlTags {
    public static String reaplaceAll(String text) {
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
