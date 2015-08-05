package com.epam.lab.spider;

/**
 * Created by hell-engine on 7/29/2015.
 */
public class SocialNetworkUtils {
    private static final Integer DEFAULT_VK_APPS_ID_AS_SITE = 5013223;
    private static final String DEFAULT_VK_SECRET_CODE_AS_SITE = "OOuLZg9pWxjjSXuvg8W6";
    private static final Integer DEFAULT_VK_APPS_ID_AS_APPS = 5013240;
//    private static final String DEFAULT_VK_SECRET_CODE_AS_APPS = "";

    public static Integer getDefaultVkAppsIdAsApps() {
        return DEFAULT_VK_APPS_ID_AS_APPS;
    }

    public static Integer getDefaultVkAppsIdAsSite() {
        return DEFAULT_VK_APPS_ID_AS_SITE;
    }

    public static String getDefaultVkSecretCodeAsSite() {
        return DEFAULT_VK_SECRET_CODE_AS_SITE;
    }

}
