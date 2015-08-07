package com.epam.lab.spider.integration.vk;

public final class Version {

    private static final String VERSION = "0.6.0";
    private static final String TITLE = "vk4j";

    public static String getVersion() {
        return VERSION;
    }

    @Override
    public String toString() {
        return TITLE + " " + VERSION;
    }

}
