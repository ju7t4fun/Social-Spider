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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Boyarsky Vitaliy on 30.06.2015.
 */
public class GroupStatsCommand implements ActionCommand {

    private static final String[] COLOR = new String[]{"#E05500", "#59FF12", "#008000", "#008B8B",
            "#0000CD", "#C71585", "#A0522D", "#00FA9A", "#FF0000", "#008080"};
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
            result.put("msg", "");
            response.getWriter().write(result.toString());
            return;
        }
        Profile profile = writeByOwnerId.get(0).getProfile();
        Vkontakte vk = new Vkontakte(4949213);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(profile.getAccessToken());
        accessToken.setUserId(profile.getVkId());
        accessToken.setExpirationMoment(profile.getExtTime());
        vk.setAccessToken(accessToken);
        Parameters param = new Parameters();
        param.add("group_id", ownerId);
        if (request.getParameter("date_from") != null) {
            param.add("date_from", request.getParameter("date_from"));
            param.add("date_to", request.getParameter("date_to"));
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            param.add("date_from", format.format(new Date(date.getTime() - 1000 * 60 * 60 * 24 * 7)));
            param.add("date_to", format.format(date));
        }
        System.out.println(param);
        List<Period> periods = null;
        try {
            periods = vk.stats().get(param);
        } catch (VKException e) {
//            e.printStackTrace();
            result.put("statuc", "error");
            result.put("msg", "Нет прав доступа");
            response.getWriter().write(result.toString());
            return;
        }
        // Діаграма відвідування
        {
            JSONObject line = new JSONObject();
            JSONArray labels = new JSONArray();
            JSONArray dataViews = new JSONArray();
            JSONArray dataVisitors = new JSONArray();
            for (Period period : periods) {
                labels.put(period.getDay());
                dataViews.put(period.getViews());
                dataVisitors.put(period.getVisitors());
            }
            JSONArray datasets = new JSONArray();
            JSONObject dataset = new JSONObject();
            dataset.put("fillColor", "rgba(220,220,220,0.5)");
            dataset.put("strokeColor", "rgba(220,220,220,1)");
            dataset.put("pointColor", "rgba(220,220,220,1)");
            dataset.put("pointStrokeColor", "#fff");
            dataset.put("data", dataViews);
            datasets.put(dataset);
            dataset = new JSONObject();
            dataset.put("fillColor", "rgba(151,187,205,0.5)");
            dataset.put("strokeColor", "rgba(151,187,205,1)");
            dataset.put("pointColor", "rgba(151,187,205,1))");
            dataset.put("pointStrokeColor", "#fff");
            dataset.put("data", dataVisitors);
            datasets.put(dataset);
            line.put("labels", labels);
            line.put("datasets", datasets);
            result.put("line", line);
        }
        // Вік користувачів
        {
            Map<String, Integer> ageVisitors = new HashMap<>();
            for (Period period : periods) {
                for (Period.Item item : period.getSexAge()) {
                    int count = 0;
                    if (ageVisitors.containsKey(item.getValue())) {
                        count = ageVisitors.get(item.getValue());
                    }
                    count = count + item.getVisitors();
                    ageVisitors.put(item.getValue(), count);
                }
            }
            JSONObject bar = new JSONObject();
            JSONArray labels = new JSONArray();
            JSONArray fData = new JSONArray();
            JSONArray mData = new JSONArray();
            List<String> keys = new ArrayList<>(ageVisitors.keySet());
            Collections.sort(keys);
            for (String value : keys) {
                String[] args = value.split(";");
                if (args[0].equals("m")) {
                    labels.put(args[1]);
                    mData.put(ageVisitors.get(value));
                } else
                    fData.put(ageVisitors.get(value));
            }
            JSONArray datasets = new JSONArray();
            JSONObject dataset = new JSONObject();
            dataset.put("fillColor", "rgba(151,187,205,0.5)");
            dataset.put("strokeColor", "rgba(151,187,205,1)");
            dataset.put("data", mData);
            datasets.put(dataset);
            dataset = new JSONObject();
            dataset.put("fillColor", "rgba(215,127,215,0.5)");
            dataset.put("strokeColor", "rgba(225,64,215,1)");
            dataset.put("data", fData);
            datasets.put(dataset);
            bar.put("labels", labels);
            bar.put("datasets", datasets);
            result.put("bar", bar);
        }
        // Країни
        {
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
                item.put("name", name);
                item.put("value", countryVisitors.get(name));
                item.put("color", COLOR[i]);
                array.put(item);
            }
            int count = 0;
            for (int i = 5; i < entries.size(); i++)
                count = count + entries.get(i).getValue();
            JSONObject item = new JSONObject();
            item.put("name", "Решта");
            item.put("value", count);
            item.put("color", "#000000");
            array.put(item);
            result.put("pie", array);
        }
        response.getWriter().write(result.toString());
    }

}
