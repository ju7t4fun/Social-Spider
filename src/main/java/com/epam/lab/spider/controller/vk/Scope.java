package com.epam.lab.spider.controller.vk;

import java.util.Locale;

public enum Scope {

    NOTIFY, FRIENDS, PHOTOS, AUDIO, VIDEO,
    DOCS, NOTES, PAGES, STATUS, WALL,
    GROUPS, MESSAGES, NOTIFICATIONS, STATS,
    ADS, OFFLINE, NOHTTPS, DIRECT;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.US);
    }

}
