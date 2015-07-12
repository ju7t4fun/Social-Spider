package com.epam.lab.spider.controller.utils;

import com.epam.lab.spider.model.vk.Period;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class StatisticsBuilder {

    private static long OFFSET = 10800000;

    private static final String[] categories = new String[]{"12-18", "18-21", "21-24", "24-27", "27-30", "30-35",
            "35-45", "45-100"};
    private List<Period> periods;

    public StatisticsBuilder(List<Period> periods) {
        this.periods = periods;
    }

    private static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

    public JSONObject buildVisitorsDiagram() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject line = new JSONObject();
        JSONArray views = new JSONArray();
        JSONArray visitors = new JSONArray();
        for (int i = periods.size() - 1; i >= 0; i--) {
            try {
                Period period = periods.get(i);
                try {
                    JSONArray row = new JSONArray();
                    Date date = format.parse(period.getDay());
                    row.put(date.getTime() + OFFSET);
                    row.put(period.getViews());
                    views.put(row);
                    row = new JSONArray();
                    row.put(date.getTime() + OFFSET);
                    row.put(period.getVisitors());
                    visitors.put(row);
                } catch (ParseException ignored) {
                }
            } catch (NullPointerException ignored) {
            }
        }
        line.put("views", views);
        line.put("visitors", visitors);
        return line;
    }

    public JSONObject buildGenderDiagram() {
        // Підраховуємо загальну кількість візитів для кожної категорії віку та розбиваємо на Ч та Ж
        Map<String, Integer> maleAgeVisits = new HashMap<>();
        Map<String, Integer> femaleAgeVisits = new HashMap<>();
        int visits = 0;
        for (Period period : periods) {
            try {
                for (Period.Item item : period.getSexAge()) {
                    int count = 0;
                    String[] key = item.getValue().split(";");
                    if (key[0].equals("m")) {
                        if (maleAgeVisits.containsKey(key[1]))
                            count = maleAgeVisits.get(key[1]);
                        count = count + item.getVisitors();
                        maleAgeVisits.put(key[1], count);
                    } else {
                        if (femaleAgeVisits.containsKey(key[1]))
                            count = femaleAgeVisits.get(key[1]);
                        count = count + item.getVisitors();
                        femaleAgeVisits.put(key[1], count);
                    }
                    visits = visits + item.getVisitors();
                }
            } catch (NullPointerException ignored) {
            }
        }
        // Заповняємо пропуски 0 та знаходимо відношення в процентах
        JSONArray male = new JSONArray();
        JSONArray female = new JSONArray();
        for (String category : categories) {
            if (maleAgeVisits.containsKey(category))
                male.put(round((float) (-100.0 * maleAgeVisits.get(category) / visits), 2));
            else
                male.put(0);
            if (femaleAgeVisits.containsKey(category))
                female.put(round((float) (100.0 * femaleAgeVisits.get(category) / visits), 2));
            else
                female.put(0);
        }

        JSONObject data = new JSONObject();
        data.put("male", male);
        data.put("female", female);
        return data;
    }

    public JSONArray buildCountryDiagram() {
        // Знаходимо загальну кількість
        Map<String, Integer> countryVisits = new HashMap<>();
        try {
            for (Period period : periods) {
                for (Period.Item item : period.getCountries()) {
                    int count = 0;
                    if (countryVisits.containsKey(item.getName()))
                        count = countryVisits.get(item.getName());
                    count = count + item.getVisitors();
                    countryVisits.put(item.getName(), count);
                }
            }
        } catch (NullPointerException ignored) {
        }
        // Сортуємо по кількості візитів
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countryVisits.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        // Записуємо не більше 5 країн
        JSONArray diagram = new JSONArray();
        for (int i = 0; i < entries.size() && i < 5; i++) {
            String name = entries.get(i).getKey();
            JSONObject item = new JSONObject();
            if (i == 0) {
                item.put("sliced", true);
                item.put("selected", true);
            }
            item.put("name", name);
            item.put("y", countryVisits.get(name));
            diagram.put(item);
        }
        // Решту візитів записуємо в категорію інші
        {
            if (entries.size() > 5) {
                int count = 0;
                for (int i = 5; i < entries.size(); i++)
                    count = count + entries.get(i).getValue();
                JSONObject item = new JSONObject();
                item.put("name", "Інші");
                item.put("y", count);
                diagram.put(item);
            }
        }
        return diagram;
    }

    public JSONArray buildCityDiagram() {
        // Знаходимо загальну кількість
        Map<String, Integer> cityVisits = new HashMap<>();
        for (Period period : periods) {
            for (Period.Item item : period.getCities()) {
                int count = 0;
                if (cityVisits.containsKey(item.getName()))
                    count = cityVisits.get(item.getName());
                count = count + item.getVisitors();
                cityVisits.put(item.getName(), count);
            }
        }
        // Сортуємо по кількості візитів
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(cityVisits.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        // Записуємо не більше 5 країн
        JSONArray diagram = new JSONArray();
        for (int i = 0; i < entries.size() && i < 5; i++) {
            String name = entries.get(i).getKey();
            JSONObject item = new JSONObject();
            if (i == 0) {
                item.put("sliced", true);
                item.put("selected", true);
            }
            item.put("name", name);
            item.put("y", cityVisits.get(name));
            diagram.put(item);
        }
        // Решту візитів записуємо в категорію інші
        {
            if (entries.size() > 5) {
                int count = 0;
                for (int i = 5; i < entries.size(); i++)
                    count = count + entries.get(i).getValue();
                JSONObject item = new JSONObject();
                item.put("name", "Інші");
                item.put("y", count);
                diagram.put(item);
            }
        }
        return diagram;
    }

}
