package com.epam.lab.spider.controller.command.owner;

import com.epam.lab.spider.controller.command.ActionCommand;
import com.epam.lab.spider.controller.utils.StatisticsBuilder;
import com.epam.lab.spider.controller.utils.UTF8;
import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.entity.Wall;
import com.epam.lab.spider.model.db.service.ServiceFactory;
import com.epam.lab.spider.model.db.service.WallService;
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
import java.util.ResourceBundle;

/**
 * Created by Boyarsky Vitaliy on 09.07.2015.
 */
public class GetGroupStatsCommand implements ActionCommand {

    private static ServiceFactory factory = ServiceFactory.getInstance();
    private static WallService wallService = factory.create(WallService.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = (ResourceBundle) session.getAttribute("bundle");
        int ownerId = Integer.parseInt(request.getParameter("id"));
        List<Wall> writeByOwnerId = wallService.getWriteByOwnerId(ownerId);
        JSONObject result = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (writeByOwnerId.size() == 0) {
            result.put("status", "error");
            result.put("msg", UTF8.encoding(bundle.getString("group.stats.nobind")));
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
        result.put("msg", UTF8.encoding(bundle.getString("group.stats.nopermission")));
        response.getWriter().write(result.toString());
    }

}
