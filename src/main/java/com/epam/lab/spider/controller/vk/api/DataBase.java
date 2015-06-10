package com.epam.lab.spider.controller.vk.api;

import com.epam.lab.spider.controller.vk.Node;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.Response;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.auth.AccessToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase extends Methods {

    public DataBase(AccessToken token) {
        super(token);
    }

    private static Map<Integer, String> parse(List<Node> nodes) {
        Map<Integer, String> object = new HashMap<Integer, String>();
        for (final Node node : nodes)
            object.put(node.child().get(0).value().toInt(),
                    node.child().get(1).value().toString());
        return object;
    }

    /**
     * Возвращает список стран.
     */
    public Map<Integer, String> getCountries(Parameters param) throws VKException {
        Response response = request("database.getCountries", param).execute();
        return parse(response.root().child("items").get(0).child("country"));
    }

    /**
     * Возвращает список стран.
     */
    public Map<Integer, String> getRegions(Parameters param) throws VKException {
        Response response = request("database.getRegions", param).execute();
        return parse(response.root().child("items").get(0).child("region"));
    }

    /**
     * Возвращает информацию об улицах по их идентификаторам (id).
     */
    public Map<Integer, String> getStreetsById(Parameters param) throws VKException {
        Response response = request("database.getStreetsById", param).execute();
        return parse(response.root().child());
    }

    /**
     * Возвращает информацию об улицах по их идентификаторам (id).
     */
    public Map<Integer, String> getCountriesById(Parameters param) throws VKException {
        Response response = request("database.getCountriesById", param).execute();
        return parse(response.root().child());
    }

    /**
     * Возвращает список городов.
     */
    public Map<Integer, String> getCities(Parameters param) throws VKException {
        Response response = request("database.getCities", param).execute();
        return parse(response.root().child("items").get(0).child("city"));
    }

    /**
     * Возвращает информацию о городах по их идентификаторам.
     */
    public Map<Integer, String> getCitiesById(Parameters param) throws VKException {
        Response response = request("database.getCitiesById", param).execute();
        return parse(response.root().child());
    }

    /**
     * Возвращает список высших учебных заведений.
     */
    public Map<Integer, String> getUniversities(Parameters param) throws VKException {
        Response response = request("database.getUniversities", param).execute();
        return parse(response.root().child("items").get(0).child());
    }

    /**
     * Возвращает список школ.
     */
    public Map<Integer, String> getSchools(Parameters param) throws VKException {
        Response response = request("database.getSchools", param).execute();
        return parse(response.root().child("items").get(0).child());
    }

    /**
     * Возвращает список классов, характерных для школ определенной страны.
     */
    public Map<Integer, String> getSchoolClasses(Parameters param) throws VKException {
        Response response = request("database.getSchoolClasses", param).execute();
        return parse(response.root().child());
    }

    /**
     * Возвращает список факультетов.
     */
    public Map<Integer, String> getFaculties(Parameters param) throws VKException {
        Response response = request("database.getFaculties", param).execute();
        return parse(response.root().child("items").get(0).child());
    }

    /**
     * Возвращает список кафедр университета по указанному факультету.
     */
    public Map<Integer, String> getChairs(Parameters param) throws VKException {
        Response response = request("database.getChairs", param).execute();
        return parse(response.root().child("items").get(0).child());
    }

}
