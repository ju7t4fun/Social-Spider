package com.epam.lab.spider.controller.vk;

import java.util.Locale;

public interface Response {

    Node root();

    public enum Type {
        XML, JSON;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.US);
        }

    }

}
