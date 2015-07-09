package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
import com.epam.lab.spider.model.vk.Period;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class GetGroupStatsCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService wallService = factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ownerId = Integer.parseInt(request.getParameter("id"));
        List<Wall> writeByOwnerId = wallService.getWriteByOwnerId(ownerId);
        JSONObject result = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (writeByOwnerId.size() == 0) {
            result.put("status", "error");
            result.put("msg", "До групи не привязано профілів");
            response.getWriter().write(result.toString());
            return;
        }
        Vkontakte vk = new Vkontakte(4949213);
        String dateFrom, dateTo;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (request.getParameter("date_from") != null) {
            dateFrom = request.getParameter("date_from");
            dateTo = request.getParameter("date_to");
        } else {
            Date date = new Date();
            dateFrom = format.format(new Date(date.getTime() - 1000 * 60 * 60 * 24 * 6));
            dateTo = format.format(date);
        }
        result.put("date_from", dateFrom);
        result.put("date_to", dateTo);
        result.put("date_max", format.format(new Date()));
        for (Wall wall : writeByOwnerId) {
            Profile profile = wall.getProfile();
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            Parameters param = new Parameters();
            param.add("group_id", Math.abs(wall.getOwner().getVkId()));
            param.add("date_from", dateFrom);
            param.add("date_to", dateTo);
            List<Period> periods;
            try {
                periods = vk.stats().get(param);
            } catch (VKException e) {
                break;
            }
            // Створення діаграм
            result.put("line", dataLineDiagram(periods));
            result.put("bar", dataGenderDiagram(periods));
            result.put("country", dataCountryDiagram(periods));
            result.put("city", dataCityDiagram(periods));
            response.getWriter().write(result.toString());
            return;
        }
        result.put("status", "error");
        result.put("msg", "У вас немає прав доступу до даної спільноти");
        response.getWriter().write(result.toString());
    }

    private JSONObject dataLineDiagram(List<Period> periods) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject line = new JSONObject();
        JSONArray views = new JSONArray();
        JSONArray visitors = new JSONArray();
        for (int i = periods.size() - 1; i > 0; i--) {
            Period period = periods.get(i);
            try {
                JSONArray row = new JSONArray();
                Date date = format.parse(period.getDay());
                row.put(date.getTime());
                row.put(1.0 * period.getViews());
                views.put(row);
                row = new JSONArray();
                row.put(date.getTime());
                row.put(1.0 * period.getVisitors());
                visitors.put(row);
            } catch (ParseException ignored) {
            }
        }
        line.put("views", views);
        line.put("visitors", visitors);
        return line;
    }

    private JSONObject dataGenderDiagram(List<Period> periods) {
        Map<String, Integer> ageVisitors = new HashMap<>();
        int allVisitors = 0;
        for (Period period : periods) {
            for (Period.Item item : period.getSexAge()) {
                int count = 0;
                if (ageVisitors.containsKey(item.getValue())) {
                    count = ageVisitors.get(item.getValue());
                }
                allVisitors = allVisitors + item.getVisitors();
                count = count + item.getVisitors();
                ageVisitors.put(item.getValue(), count);
            }
        }
        JSONObject bar = new JSONObject();
        JSONArray female = new JSONArray();
        JSONArray male = new JSONArray();
        List<String> keys = new ArrayList<>(ageVisitors.keySet());
        Collections.sort(keys);
        for (String value : keys) {
            String[] args = value.split(";");
            if (args[0].equals("m")) {
                male.put(round((float) (-1.0 * ageVisitors.get(value) / allVisitors * 100), 2));
            } else
                female.put(round((float) (1.0 * ageVisitors.get(value) / allVisitors * 100), 2));
        }
        bar.put("female", female);
        bar.put("male", male);
        return bar;
    }

    private JSONArray dataCountryDiagram(List<Period> periods) {
        Map<String, Integer> countryVisitors = new HashMap<>();
        for (Period period : periods) {
            for (Period.Item item : period.getCountries()) {
                int count = 0;
                if (countryVisitors.containsKey(item.getName())) {
                    count = countryVisitors.get(item.getName());
                }
                count = count + item.getVisitors();
                countryVisitors.put(item.getName(), count);
            }
        }
        JSONArray array = new JSONArray();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countryVisitors.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        for (int i = 0; i < 5; i++) {
            String name = entries.get(i).getKey();
            JSONObject item = new JSONObject();
            if (i == 0) {
                item.put("sliced", true);
                item.put("selected", true);
            }
            item.put("name", name);
            item.put("y", countryVisitors.get(name));
            array.put(item);
        }
        int count = 0;
        for (int i = 5; i < entries.size(); i++)
            count = count + entries.get(i).getValue();
        JSONObject item = new JSONObject();
        item.put("name", "Інші");
        item.put("y", count);
        array.put(item);
        return array;
    }

    private JSONArray dataCityDiagram(List<Period> periods) {
        Map<String, Integer> cityVisitors = new HashMap<>();
        for (Period period : periods) {
            for (Period.Item item : period.getCities()) {
                int count = 0;
                if (cityVisitors.containsKey(item.getName())) {
                    count = cityVisitors.get(item.getName());
                }
                count = count + item.getVisitors();
                cityVisitors.put(item.getName(), count);
            }
        }
        JSONArray array = new JSONArray();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(cityVisitors.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
        for (int i = 0; i < 5; i++) {
            String name = entries.get(i).getKey();
            JSONObject item = new JSONObject();
            if (i == 0) {
                item.put("sliced", true);
                item.put("selected", true);
            }
            item.put("name", name);
            item.put("y", cityVisitors.get(name));
            array.put(item);
        }
        int count = 0;
        for (int i = 5; i < entries.size(); i++)
            count = count + entries.get(i).getValue();
        JSONObject item = new JSONObject();
        item.put("name", "Інші");
        item.put("y", count);
        array.put(item);
        return array;
    }

    public static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

}
