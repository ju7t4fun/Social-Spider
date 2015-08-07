package com.epam.lab.spider.integration.vk;

import java.net.URL;
import java.util.Date;

public interface Value {

    boolean toBoolean();

    byte toByte();

    double toDouble();

    int toInt();

    long toLong();

    String toString();

    URL toURL();

    Date toDate();

}
