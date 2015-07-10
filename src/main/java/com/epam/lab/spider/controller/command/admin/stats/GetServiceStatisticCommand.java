package com.epam.lab.spider.controller.command.admin.stats;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.model.db.service.EventService;
import com.epam.lab.spider.model.db.service.NewPostService;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.TaskSynchronizedDataService;
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
 * Created by Boyarsky Vitaliy on 10.07.2015.
 */
public class GetServiceStatisticCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date;
        if (request.getParameter("date") == null)
            date = format.format(new Date());
        else
            date = request.getParameter("date");
        json.put("task", buildTaskExecutionDiagram(date));
        json.put("errors", buildErrorDiagram(date));
        json.put("posted", buildPostDiagram(date));
        json.put("date", date);
        response.getWriter().write(json.toString());
    }

    private JSONArray buildTaskExecutionDiagram(String date) {
        TaskSynchronizedDataService service = new TaskSynchronizedDataService();
        Map<Long, Integer> statistics = service.statisticsExecution(date);
        return buildDiagram(statistics, date);
    }

    private JSONArray buildErrorDiagram(String date) {
        EventService service = factory.create(EventService.class);
        Map<Long, Integer> statistics = service.statisticsError(date);
        return buildDiagram(statistics, date);
    }

    private JSONArray buildPostDiagram(String date) {
        NewPostService service = factory.create(NewPostService.class);
        Map<Long, Integer> statistics = service.statisticsPosting(date);
        return buildDiagram(statistics, date);
    }

    private JSONArray buildDiagram(Map<Long, Integer> statistics, String date) {
        JSONArray array = new JSONArray();
        List<Long> keys = new ArrayList<>(statistics.keySet());
        Collections.sort(keys);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long cur = 0;
        try {
            cur = dateFormat.parse(date).getTime();
        } catch (ParseException ignored) {
        }
        long end = cur + 86400000;
        while (cur <= end) {
            JSONArray row = new JSONArray();
            if (statistics.containsKey(cur)) {
                row.put(cur);
                row.put(statistics.get(cur));
            } else {
                row.put(cur);
                row.put(0);
            }
            array.put(row);
            cur = cur + 360000;
        }
        return array;
    }

}
