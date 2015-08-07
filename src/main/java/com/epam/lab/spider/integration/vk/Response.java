package com.epam.lab.spider.integration.vk;

import java.util.Locale;

public interface Response {

    Node root();

    enum Type {
        XML, JSON;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.US);
        }

    }

}
