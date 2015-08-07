package com.epam.lab.spider.integration.vk;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class NodeJson implements Node {
    @Override
    public String name() {
        return null;
    }

    @Override
    public Value value() {
        return new Value() {
            @Override
            public boolean toBoolean() {
                return false;
            }

            @Override
            public byte toByte() {
                return 0;
            }

            @Override
            public double toDouble() {
                return 0;
            }

            @Override
            public int toInt() {
                return 0;
            }

            @Override
            public long toLong() {
                return 0;
            }

            @Override
            public URL toURL() {
                return null;
            }

            @Override
            public Date toDate() {
                return null;
            }
        };
    }

    @Override
    public boolean hasChild() {
        return false;
    }

    @Override
    public boolean hasChild(String name) {
        return false;
    }

    @Override
    public List<Node> child() {
        return null;
    }

    @Override
    public List<Node> child(String name) {
        return null;
    }

    @Override
    public String parse(String key) {
        return null;
    }
}
