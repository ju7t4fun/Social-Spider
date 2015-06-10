package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Period extends Model {

    public static final String DAY = "day";
    public static final String VIEWS = "views";
    public static final String VISITORS = "visitors";
    public static final String REACH = "reach";
    public static final String REACH_SUBSCRIBERS = "reach_subscribers";

    private List<Item> sex, age, sexAge, cities, countries;

    public Period() {
        super();
    }

    public Period(Node root) {
        super(root, Period.class);
        if (root.hasChild("sex"))
            sex = Item.parseItem(root.child("sex").get(0));
        if (root.hasChild("age"))
            age = Item.parseItem(root.child("age").get(0));
        if (root.hasChild("sex_age"))
            sexAge = Item.parseItem(root.child("sex_age").get(0));
        if (root.hasChild("cities"))
            cities = Item.parseItem(root.child("cities").get(0));
        if (root.hasChild("countries"))
            countries = Item.parseItem(root.child("countries").get(0));
    }

    public static List<Period> parsePeriod(Node root) {
        List<Period> periods = new ArrayList<Period>();
        List<Node> nodes = root.child("period");
        for (Node node : nodes)
            periods.add(new Period(node));
        return periods;
    }

    public String getDay() {
        return get(DAY).toString();
    }

    public int getViews() {
        return get(VIEWS).toInt();
    }

    public int getVisitors() {
        return get(VISITORS).toInt();
    }

    public int getReach() {
        return get(REACH).toInt();
    }

    public int getReachSubscribers() {
        return get(REACH_SUBSCRIBERS).toInt();
    }

    public List<Item> getSex() {
        return sex;
    }

    public List<Item> getAge() {
        return age;
    }

    public List<Item> getSexAge() {
        return sexAge;
    }

    public List<Item> getCities() {
        return cities;
    }

    public List<Item> getCountries() {
        return countries;
    }

    public static class Item extends Model {

        public static final String VISITORS = "visitors";
        public static final String VALUE = "value";
        public static final String NAME = "name";

        public Item() {
            super();
        }

        public Item(Node root) {
            super(root, Item.class);
        }

        public static List<Item> parseItem(Node root) {
            List<Item> items = new ArrayList<Item>();
            List<Node> nodes = root.child("item");
            for (Node node : nodes)
                items.add(new Item(node));
            return items;
        }

        public int getVisitors() {
            return get(VISITORS).toInt();
        }

        public String getValue() {
            return get(VALUE).toString();
        }

        public String getName() {
            return get(NAME).toString();
        }

    }

}
