package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.controller.vk.Node;

import java.util.ArrayList;
import java.util.List;

public class Place extends Model {

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String TITLE = "title";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CREATED = "created";
    public static final String CHECKINS = "checkins";
    public static final String UPDATED = "updated";
    public static final String TYPE = "type";
    public static final String COUNTRY = "country";
    public static final String CITY = "city";
    public static final String ADDRESS = "address";
    public static final String ICON = "icon";
    public static final String DISTANCE = "distance";

    public Place() {
        super();
    }

    public Place(Node root) {
        super(root, Place.class);
    }

    public static List<Place> parsePlace(Node root) {
        List<Place> places = new ArrayList<Place>();
        List<Node> nodes = root.child("place");
        for (Node node : nodes)
            places.add(new Place(node));
        return places;
    }


}
