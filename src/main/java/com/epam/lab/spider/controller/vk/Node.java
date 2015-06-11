package com.epam.lab.spider.controller.vk;

import java.util.List;

public interface Node {

    String name();
    Value value();
    boolean hasChild();
    boolean hasChild(String name);
    List<Node> child();
    List<Node> child(String name);
    String parse(String key);

}
