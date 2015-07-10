package com.epam.lab.spider.controller.command.admin.stats;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.StatisticsBuilder;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.User;
import com.epam.lab.spider.model.vk.Period;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class GetVisitorsStatsCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject result = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Set<Profile> profiles = user.getProfiles();
        Parameters param = new Parameters();
        Vkontakte vk = new Vkontakte(4949213);
        param.add("app_id", 4949213);
        param.add("date_from", dateFrom);
        param.add("date_to", dateTo);
        for (Profile profile : profiles) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime());
            vk.setAccessToken(accessToken);
            List<Period> periods;
            try {
                periods = vk.stats().get(param);
            } catch (VKException e) {
                continue;
            }
            StatisticsBuilder stats = new StatisticsBuilder(periods);
            result.put("line", stats.buildVisitorsDiagram());
            result.put("bar", stats.buildGenderDiagram());
            result.put("country", stats.buildCountryDiagram());
            result.put("city", stats.buildCityDiagram());
            response.getWriter().write(result.toString());
            return;
        }
        result.put("status", "error");
        result.put("msg", "У вас немає прав доступу до додатку");
        response.getWriter().write(result.toString());
    }


}
