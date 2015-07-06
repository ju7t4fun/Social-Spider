package com.epam.lab.spider.controller.command.admin;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.User;

import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
import com.epam.lab.spider.model.vk.Period;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Орест on 7/2/2015.
 */
public class AdminVkGroupStats implements ActionCommand {
    private static final String[] COLOR = new String[]{"#E05500", "#59FF12", "#008000", "#008B8B",
            "#0000CD", "#C71585", "#A0522D", "#00FA9A", "#FF0000", "#008080"};
    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        JSONObject result = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println("In SERVLEt vkgroups befre loop");

        Vkontakte vk = new Vkontakte(4949213);
        String dateFrom, dateTo;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (request.getParameter("date_from") != null) {
            dateFrom = request.getParameter("date_from");
            dateTo = request.getParameter("date_to");
        } else {
            Date date = new Date();
            dateFrom = format.format(new Date(date.getTime() - 1000 * 60 * 60 * 24 * 500));
            dateTo = format.format(date);
        }
        result.put("date_from", dateFrom);
        result.put("date_to", dateTo);
        result.put("date_max", format.format(new Date()));


        User user = (User)request.getSession().getAttribute("user");
        Set<Profile> profiles =  user.getProfiles();


        for (Profile profile : profiles) {

            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            Parameters param = new Parameters();
            //4949208
            param.add("app_id", 4798702);
            param.add("date_from", dateFrom);
            param.add("date_to", dateTo);
            List<Period> periods;
            try {
                periods = vk.stats().get(param);
            } catch (VKException e) {
                continue;
            }
            System.out.println("In SERVLEt vkgroups");
            // Створення діаграм
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
            {
                result.put("day", periods.size());
                int visitors = sum(periods);
                result.put("visitors", visitors);
                result.put("dayVisitors", visitors / periods.size());
            }
            System.out.println("REESULT : " + result.toString());
            response.getWriter().write(result.toString());
            return;
        }
        result.put("status", "error");
        result.put("msg", UTF8.encoding(bundle.getString("notification.have.no.access.rights")));
        System.out.println("PO PEZDI");
        response.getWriter().write(result.toString());
    }

    private int sum(List<Period> periods) {
        int sum = 0;
        for (Period period : periods)
            sum = sum + period.getVisitors();
        return sum;
    }
}
